package com.jpg.drone_tracker.application.component.drone.service;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.domain.DroneMedication;
import com.jpg.drone_tracker.application.component.drone.domain.DroneModel;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;
import com.jpg.drone_tracker.application.component.drone.repository.DroneMedicationRepository;
import com.jpg.drone_tracker.application.component.drone.repository.DroneRepository;
import com.jpg.drone_tracker.application.component.medication.domain.Medication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DroneDetailsServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private DroneMedicationRepository droneMedicationRepository;

    @InjectMocks
    private DroneDetailsService droneDetailsService;

    @Test
    void getLoadedMedications_returnsMedicationList() {
        Drone drone = new Drone("DRONE-20260711-101", DroneModel.HEAVYWEIGHT, 80, DroneState.LOADED);
        Medication medication = new Medication("PainRelief", 250, "MED_1", "images/med1.png");
        DroneMedication droneMedication = new DroneMedication(drone, medication);

        when(droneRepository.findBySerialNumber(drone.getSerialNumber())).thenReturn(Optional.of(drone));
        when(droneMedicationRepository.findAllByDrone_Id(drone.getId())).thenReturn(List.of(droneMedication));

        List<Medication> medications = droneDetailsService.getLoadedMedications(drone.getSerialNumber());

        assertEquals(1, medications.size());
        assertEquals(medication.getCode(), medications.getFirst().getCode());
        verify(droneMedicationRepository).findAllByDrone_Id(drone.getId());
    }

    @Test
    void isAvailableForLoading_returnsTrueWhenDroneCanLoad() {
        Drone drone = new Drone("DRONE-20260711-102", DroneModel.HEAVYWEIGHT, 80, DroneState.IDLE);

        when(droneRepository.findBySerialNumberWithLoadedMedications(drone.getSerialNumber()))
                .thenReturn(Optional.of(drone));

        assertTrue(droneDetailsService.isAvailableForLoading(drone.getSerialNumber()));
        verify(droneRepository).findBySerialNumberWithLoadedMedications(drone.getSerialNumber());
    }

    @Test
    void isAvailableForLoading_returnsFalseWhenBatteryIsTooLow() {
        Drone drone = new Drone("DRONE-20260711-103", DroneModel.HEAVYWEIGHT, 24, DroneState.IDLE);

        when(droneRepository.findBySerialNumberWithLoadedMedications(drone.getSerialNumber()))
                .thenReturn(Optional.of(drone));

        assertFalse(droneDetailsService.isAvailableForLoading(drone.getSerialNumber()));
    }

    @Test
    void getBatteryCapacity_returnsBatteryLevel() {
        Drone drone = new Drone("DRONE-20260711-104", DroneModel.CRUISERWEIGHT, 67, DroneState.IDLE);

        when(droneRepository.findBySerialNumber(drone.getSerialNumber())).thenReturn(Optional.of(drone));

        assertEquals(67, droneDetailsService.getBatteryCapacity(drone.getSerialNumber()));
    }

    @Test
    void getBatteryCapacity_throwsWhenDroneNotFound() {
        when(droneRepository.findBySerialNumber("missing")).thenReturn(Optional.empty());

        assertThrows(DroneDetailsException.class, () -> droneDetailsService.getBatteryCapacity("missing"));
        verify(droneMedicationRepository, never()).findAllByDrone_Id(any());
    }
}
