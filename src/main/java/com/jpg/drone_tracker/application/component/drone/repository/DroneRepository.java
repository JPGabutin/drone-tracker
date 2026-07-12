package com.jpg.drone_tracker.application.component.drone.repository;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface DroneRepository extends JpaRepository<Drone, UUID> {

    Optional<Drone> findBySerialNumber(String serialNumber);

    @EntityGraph(attributePaths = {"loadedMedications", "loadedMedications.medication"})
    @Query("select d from Drone d where d.serialNumber = :serialNumber")
    Optional<Drone> findBySerialNumberWithLoadedMedications(@Param("serialNumber") String serialNumber);

    Collection<Drone> findAllByStateInAndStateChangedAtBefore(Collection<DroneState> states, Instant stateChangedAt);
}
