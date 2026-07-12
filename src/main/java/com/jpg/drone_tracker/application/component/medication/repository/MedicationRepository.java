package com.jpg.drone_tracker.application.component.medication.repository;

import com.jpg.drone_tracker.application.component.medication.domain.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, UUID> {

    Optional<Medication> findByCode(String code);
}
