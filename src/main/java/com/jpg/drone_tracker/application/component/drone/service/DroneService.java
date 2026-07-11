package com.jpg.drone_tracker.application.component.drone.service;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;
import com.jpg.drone_tracker.application.component.drone.repository.DroneRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DroneService {

    private final DroneRepository droneRepository;

    public DroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Transactional
    public Drone registerDrone(RegisterDroneCommand command) {
        if (droneRepository.findBySerialNumber(command.serialNumber()).isPresent()) {
            throw new IllegalArgumentException("drone serial number already exists");
        }
        return droneRepository.save(new Drone(
                command.serialNumber(),
                command.model(),
                100,
                DroneState.IDLE
        ));
    }
}
