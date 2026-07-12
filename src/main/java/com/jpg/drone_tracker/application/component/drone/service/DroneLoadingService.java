package com.jpg.drone_tracker.application.component.drone.service;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.domain.DroneMedication;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;
import com.jpg.drone_tracker.application.component.drone.repository.DroneRepository;
import com.jpg.drone_tracker.application.component.medication.domain.Medication;
import com.jpg.drone_tracker.application.component.medication.repository.MedicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DroneLoadingService {

    private static final int MIN_BATTERY_FOR_LOADING = 25;

    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;

    public DroneLoadingService(
            DroneRepository droneRepository,
            MedicationRepository medicationRepository
    ) {
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }

    @Transactional
    public Drone startLoading(LoadDroneMedicationCommand command) {
        Drone drone = droneRepository.findBySerialNumber(command.droneSerialNumber())
                .orElseThrow(() -> new DroneLoadingException("drone not found"));

        if (drone.getBatteryCapacity() < MIN_BATTERY_FOR_LOADING) {
            throw new DroneLoadingException("drone battery is below loading threshold");
        }

        if (!drone.getState().canTransitionTo(DroneState.LOADING) && drone.getState() != DroneState.LOADING) {
            throw new DroneLoadingException("drone is not available for loading");
        }

        Medication medication = medicationRepository.findByCode(command.medicationCode())
                .orElseThrow(() -> new DroneLoadingException("medication not found: " + command.medicationCode()));

        if (droneMedicationRepository.existsByDrone_IdAndMedication_Code(drone.getId(), medication.getCode())) {
            if (drone.getState() == DroneState.IDLE) {
                drone.setState(DroneState.LOADING);
            }
            return droneRepository.save(drone);
        }

        if (drone.getCurrentLoadWeight() + medication.getWeight() > drone.getWeightLimit()) {
            throw new DroneLoadingException("drone would exceed maximum weight capacity");
        }

        drone.loadMedication(medication);
        drone.setState(DroneState.LOADING);
        return droneRepository.save(drone);
    }

    @Transactional
    public Drone completeLoading(String droneSerialNumber) {
        Drone drone = droneRepository.findBySerialNumber(droneSerialNumber)
                .orElseThrow(() -> new DroneLoadingException("drone not found"));

        if (!drone.getState().canTransitionTo(DroneState.LOADED)) {
            throw new DroneLoadingException("drone loading is not in progress");
        }

        if (drone.getLoadedMedications().isEmpty()) {
            throw new DroneLoadingException("drone has no loaded medications");
        }

        drone.setState(DroneState.LOADED);
        return droneRepository.save(drone);
    }
}
