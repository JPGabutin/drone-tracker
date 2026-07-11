package com.jpg.drone_tracker.application.component.drone.service;

import com.jpg.drone_tracker.application.component.drone.domain.DroneModel;

public record RegisterDroneCommand(
        String serialNumber,
        DroneModel model
) {
}
