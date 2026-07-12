package com.jpg.drone_tracker.application.component.drone.service;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;
import com.jpg.drone_tracker.application.component.drone.repository.DroneRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class DroneStateTransitionScheduler {

    private static final Duration STATE_DURATION = Duration.ofMinutes(1);

    private final DroneRepository droneRepository;

    public DroneStateTransitionScheduler(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void advanceTimedDroneStates() {
        Instant transitionDeadline = Instant.now().minus(STATE_DURATION);
        List<DroneState> timedStates = List.of(
                DroneState.LOADED,
                DroneState.DELIVERING,
                DroneState.DELIVERED,
                DroneState.RETURNING
        );

        for (Drone drone : droneRepository.findAllByStateInAndStateChangedAtBefore(timedStates, transitionDeadline)) {
            advanceDroneState(drone);
        }
    }

    private void advanceDroneState(Drone drone) {
        drone.getState().nextTimedState().ifPresent(nextState -> {
            drone.setState(nextState);

            if (nextState == DroneState.RETURNING) {
                drone.setBatteryCapacity(Math.max(0, drone.getBatteryCapacity() - 25));
            }

            droneRepository.save(drone);
        });
    }
}
