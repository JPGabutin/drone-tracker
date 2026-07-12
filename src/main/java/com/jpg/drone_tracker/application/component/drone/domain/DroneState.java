package com.jpg.drone_tracker.application.component.drone.domain;

import java.util.Optional;

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

    public boolean isTimedTransitionState() {
        return switch (this) {
            case LOADED, DELIVERING, DELIVERED, RETURNING -> true;
            case IDLE, LOADING -> false;
        };
    }

    public Optional<DroneState> nextTimedState() {
        return switch (this) {
            case LOADED -> Optional.of(DELIVERING);
            case DELIVERING -> Optional.of(DELIVERED);
            case DELIVERED -> Optional.of(RETURNING);
            case RETURNING -> Optional.of(IDLE);
            case IDLE, LOADING -> Optional.empty();
        };
    }
}
