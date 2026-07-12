package com.jpg.drone_tracker.application.component.drone.api.dto;

public record DroneBatteryResponse(
        String serialNumber,
        int batteryCapacity
) {
}
