package com.jpg.drone_tracker.application.component.drone.domain;

public enum DroneState {
    IDLE,
    LOADING,
    LOADED,
    DELIVERING,
    DELIVERED,
    RETURNING;

    public boolean canTransitionTo(DroneState nextState) {
        return switch (this) {
            case IDLE -> nextState == LOADING;
            case LOADING -> nextState == LOADED;
            case LOADED -> nextState == DELIVERING;
            case DELIVERING -> nextState == DELIVERED;
            case DELIVERED -> nextState == RETURNING;
            case RETURNING -> nextState == IDLE;
        };
    }
}
