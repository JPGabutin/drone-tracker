package com.jpg.drone_tracker.application.component.drone.service;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.domain.DroneModel;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;
import com.jpg.drone_tracker.application.component.drone.api.exception.DroneSerialAlreadyExistsException;
import com.jpg.drone_tracker.application.component.drone.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    @InjectMocks
    private DroneService droneService;

    @Test
    void registerDrone_savesDroneWithDefaultBatteryAndState() {
        RegisterDroneCommand command = new RegisterDroneCommand("DRONE-20260711-001", DroneModel.CRUISERWEIGHT);
        when(droneRepository.findBySerialNumber(command.serialNumber())).thenReturn(Optional.empty());
        when(droneRepository.save(org.mockito.ArgumentMatchers.any(Drone.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Drone savedDrone = droneService.registerDrone(command);

        assertEquals(command.serialNumber(), savedDrone.getSerialNumber());
        assertEquals(command.model(), savedDrone.getModel());
        assertEquals(100, savedDrone.getBatteryCapacity());
        assertEquals(DroneState.IDLE, savedDrone.getState());
        verify(droneRepository).save(org.mockito.ArgumentMatchers.any(Drone.class));
    }

    @Test
    void registerDrone_throwsWhenSerialAlreadyExists() {
        RegisterDroneCommand command = new RegisterDroneCommand("DRONE-20260711-001", DroneModel.CRUISERWEIGHT);
        when(droneRepository.findBySerialNumber(command.serialNumber()))
                .thenReturn(Optional.of(new Drone("DRONE-20260711-001", DroneModel.LIGHTWEIGHT, 100, DroneState.IDLE)));

        assertThrows(DroneSerialAlreadyExistsException.class, () -> droneService.registerDrone(command));

        verify(droneRepository, never()).save(org.mockito.ArgumentMatchers.any(Drone.class));
    }
}
