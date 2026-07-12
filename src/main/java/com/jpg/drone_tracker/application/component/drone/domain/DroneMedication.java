package com.jpg.drone_tracker.application.component.drone.domain;

import com.jpg.drone_tracker.application.component.medication.domain.Medication;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "drone_medications")
public class DroneMedication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drone_id", nullable = false)
    private Drone drone;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    protected DroneMedication() {
    }

    public DroneMedication(Drone drone, Medication medication) {
        this.drone = Objects.requireNonNull(drone, "drone must not be null");
        this.medication = Objects.requireNonNull(medication, "medication must not be null");
    }

    public UUID getId() {
        return id;
    }

    public Drone getDrone() {
        return drone;
    }

    public Medication getMedication() {
        return medication;
    }
}
