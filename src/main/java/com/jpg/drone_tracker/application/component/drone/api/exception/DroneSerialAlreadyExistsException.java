package com.jpg.drone_tracker.application.component.drone.api.exception;

public class DroneSerialAlreadyExistsException extends RuntimeException {

    public DroneSerialAlreadyExistsException(String serialNumber) {
        super("drone serial number already exists: " + serialNumber);
    }
}
