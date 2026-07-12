package com.jpg.drone_tracker.application.component.drone.api.dto;

public record DroneAvailabilityResponse(
        String serialNumber,
        boolean availableForLoading
) {
}
