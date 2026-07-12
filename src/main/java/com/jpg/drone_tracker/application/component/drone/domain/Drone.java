package com.jpg.drone_tracker.application.component.drone.domain;

import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.jpg.drone_tracker.application.component.medication.domain.Medication;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "drones")
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100, unique = true)
    private String serialNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DroneModel model;

    @Min(0)
    @Max(100)
    @Column(nullable = false)
    private int batteryCapacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DroneState state;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DroneMedication> loadedMedications = new ArrayList<>();

    protected Drone() {
    }

    public Drone(String serialNumber, DroneModel model, int batteryCapacity, DroneState state) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.batteryCapacity = batteryCapacity;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public DroneModel getModel() {
        return model;
    }

    public int getWeightLimit() {
        return model.getWeightLimit();
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public DroneState getState() {
        return state;
    }

    public List<DroneMedication> getLoadedMedications() {
        return Collections.unmodifiableList(loadedMedications);
    }

    public int getCurrentLoadWeight() {
        return loadedMedications.stream()
                .mapToInt(loadedMedication -> loadedMedication.getMedication().getWeight())
                .sum();
    }

    public DroneMedication loadMedication(Medication medication) {
        DroneMedication loadedMedication = new DroneMedication(this, medication);
        loadedMedications.add(loadedMedication);
        return loadedMedication;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public void setState(DroneState state) {
        this.state = state;
    }
}
