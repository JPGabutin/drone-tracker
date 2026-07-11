package com.jpg.drone_tracker.application.component.drone.domain;

public enum DroneModel {
    LIGHTWEIGHT(250),
    MIDDLEWEIGHT(500),
    CRUISERWEIGHT(750),
    HEAVYWEIGHT(1000);

    private final int weightLimit;

    DroneModel(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public int getWeightLimit() {
        return weightLimit;
    }
}
