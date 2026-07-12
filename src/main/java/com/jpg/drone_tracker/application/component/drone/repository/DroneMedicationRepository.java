package com.jpg.drone_tracker.application.component.drone.repository;

import com.jpg.drone_tracker.application.component.drone.domain.DroneMedication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DroneMedicationRepository extends JpaRepository<DroneMedication, UUID> {

    List<DroneMedication> findAllByDrone_Id(UUID droneId);

    void deleteAllByDrone_Id(UUID droneId);
}
