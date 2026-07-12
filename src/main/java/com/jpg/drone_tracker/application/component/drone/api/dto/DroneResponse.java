package com.jpg.drone_tracker.application.component.drone.api.dto;

import com.jpg.drone_tracker.application.component.drone.domain.DroneModel;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;

import java.util.UUID;

public record DroneResponse(
        UUID id,
        String serialNumber,
        DroneModel model,
        int weightLimit,
        int currentLoadWeight,
        int batteryCapacity,
        DroneState state
) {
}
