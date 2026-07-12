package com.jpg.drone_tracker.application.component.drone.service;

public record LoadDroneMedicationCommand(
        String droneSerialNumber,
        String medicationCode
) {
}
