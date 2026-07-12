package com.jpg.drone_tracker.application.component.drone.service;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.domain.DroneMedication;
import com.jpg.drone_tracker.application.component.drone.domain.DroneModel;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;
import com.jpg.drone_tracker.application.component.drone.repository.DroneMedicationRepository;
import com.jpg.drone_tracker.application.component.drone.repository.DroneRepository;
import com.jpg.drone_tracker.application.component.medication.domain.Medication;
import com.jpg.drone_tracker.application.component.medication.repository.MedicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DroneLoadingServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private DroneMedicationRepository droneMedicationRepository;

    @Mock
    private MedicationRepository medicationRepository;

    @InjectMocks
    private DroneLoadingService droneLoadingService;

    @Test
    void startLoading_addsMedicationAndSetsLoadingState() {
        Drone drone = new Drone("DRONE-20260711-001", DroneModel.HEAVYWEIGHT, 50, DroneState.IDLE);
        Medication medication = new Medication("med1", 250, "MED_1", "images/med1.png");
        LoadDroneMedicationCommand command = new LoadDroneMedicationCommand(drone.getSerialNumber(), medication.getCode());

        when(droneRepository.findBySerialNumber(command.droneSerialNumber())).thenReturn(Optional.of(drone));
        when(medicationRepository.findByCode(command.medicationCode())).thenReturn(Optional.of(medication));
        when(droneMedicationRepository.existsByDrone_IdAndMedication_Code(drone.getId(), medication.getCode()))
                .thenReturn(false);
        when(droneRepository.save(any(Drone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Drone loadedDrone = droneLoadingService.startLoading(command);

        assertEquals(DroneState.LOADING, loadedDrone.getState());
        assertEquals(1, loadedDrone.getLoadedMedications().size());
        assertEquals(250, loadedDrone.getCurrentLoadWeight());
        verify(droneRepository).save(any(Drone.class));
    }

    @Test
    void startLoading_isIdempotentWhenMedicationIsAlreadyLoaded() {
        Drone drone = new Drone("DRONE-20260711-002", DroneModel.HEAVYWEIGHT, 50, DroneState.LOADING);
        Medication medication = new Medication("med1", 250, "MED_1", "images/med1.png");
        drone.loadMedication(medication);
        LoadDroneMedicationCommand command = new LoadDroneMedicationCommand(drone.getSerialNumber(), medication.getCode());

        when(droneRepository.findBySerialNumber(command.droneSerialNumber())).thenReturn(Optional.of(drone));
        when(medicationRepository.findByCode(command.medicationCode())).thenReturn(Optional.of(medication));
        when(droneMedicationRepository.existsByDrone_IdAndMedication_Code(drone.getId(), medication.getCode()))
                .thenReturn(true);
        when(droneRepository.save(any(Drone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Drone loadedDrone = droneLoadingService.startLoading(command);

        assertEquals(DroneState.LOADING, loadedDrone.getState());
        assertEquals(1, loadedDrone.getLoadedMedications().size());
        verify(droneRepository).save(any(Drone.class));
    }

    @Test
    void startLoading_throwsWhenBatteryIsBelowThreshold() {
        Drone drone = new Drone("DRONE-20260711-003", DroneModel.HEAVYWEIGHT, 24, DroneState.IDLE);
        LoadDroneMedicationCommand command = new LoadDroneMedicationCommand(drone.getSerialNumber(), "MED_1");

        when(droneRepository.findBySerialNumber(command.droneSerialNumber())).thenReturn(Optional.of(drone));

        assertThrows(DroneLoadingException.class, () -> droneLoadingService.startLoading(command));
        verify(droneRepository, never()).save(any(Drone.class));
    }

    @Test
    void startLoading_throwsWhenCapacityWouldBeExceeded() {
        Drone drone = new Drone("DRONE-20260711-004", DroneModel.CRUISERWEIGHT, 50, DroneState.IDLE);
        Medication existingMedication = new Medication("existing", 600, "MED_EXIST", "images/existing.png");
        Medication newMedication = new Medication("new", 200, "MED_NEW", "images/new.png");
        drone.loadMedication(existingMedication);
        LoadDroneMedicationCommand command = new LoadDroneMedicationCommand(drone.getSerialNumber(), newMedication.getCode());

        when(droneRepository.findBySerialNumber(command.droneSerialNumber())).thenReturn(Optional.of(drone));
        when(medicationRepository.findByCode(command.medicationCode())).thenReturn(Optional.of(newMedication));
        when(droneMedicationRepository.existsByDrone_IdAndMedication_Code(drone.getId(), newMedication.getCode()))
                .thenReturn(false);

        assertThrows(DroneLoadingException.class, () -> droneLoadingService.startLoading(command));
        verify(droneRepository, never()).save(any(Drone.class));
    }

    @Test
    void completeLoading_movesDroneToLoaded() {
        Drone drone = new Drone("DRONE-20260711-005", DroneModel.HEAVYWEIGHT, 50, DroneState.LOADING);
        drone.loadMedication(new Medication("med1", 250, "MED_1", "images/med1.png"));

        when(droneRepository.findBySerialNumber(drone.getSerialNumber())).thenReturn(Optional.of(drone));
        when(droneRepository.save(any(Drone.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Drone completedDrone = droneLoadingService.completeLoading(drone.getSerialNumber());

        assertEquals(DroneState.LOADED, completedDrone.getState());
        verify(droneRepository).save(any(Drone.class));
    }
}
