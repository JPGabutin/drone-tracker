package com.jpg.drone_tracker.application.component.drone.repository;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DroneRepository extends JpaRepository<Drone, UUID> {
}
