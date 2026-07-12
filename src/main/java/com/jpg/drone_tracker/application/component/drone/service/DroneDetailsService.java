package com.jpg.drone_tracker.application.component.drone.service;

import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.domain.DroneMedication;
import com.jpg.drone_tracker.application.component.drone.domain.DroneState;
import com.jpg.drone_tracker.application.component.drone.repository.DroneMedicationRepository;
import com.jpg.drone_tracker.application.component.drone.repository.DroneRepository;
import com.jpg.drone_tracker.application.component.medication.domain.Medication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DroneDetailsService {

    private static final int MIN_BATTERY_FOR_LOADING = 25;

    private final DroneRepository droneRepository;
    private final DroneMedicationRepository droneMedicationRepository;

    public DroneDetailsService(DroneRepository droneRepository, DroneMedicationRepository droneMedicationRepository) {
        this.droneRepository = droneRepository;
        this.droneMedicationRepository = droneMedicationRepository;
    }

    @Transactional(readOnly = true)
    public List<Medication> getLoadedMedications(String droneSerialNumber) {
        Drone drone = findDroneBySerialNumber(droneSerialNumber);
        return droneMedicationRepository.findAllByDrone_Id(drone.getId())
                .stream()
                .map(DroneMedication::getMedication)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean isAvailableForLoading(String droneSerialNumber) {
        Drone drone = findDroneWithLoadedMedications(droneSerialNumber);
        return (drone.getState() == DroneState.IDLE || drone.getState() == DroneState.LOADING)
                && drone.getBatteryCapacity() >= MIN_BATTERY_FOR_LOADING
                && drone.getCurrentLoadWeight() < drone.getWeightLimit();
    }

    @Transactional(readOnly = true)
    public int getBatteryCapacity(String droneSerialNumber) {
        return findDroneBySerialNumber(droneSerialNumber).getBatteryCapacity();
    }

    private Drone findDroneBySerialNumber(String droneSerialNumber) {
        return droneRepository.findBySerialNumber(droneSerialNumber)
                .orElseThrow(() -> new DroneDetailsException("drone not found"));
    }

    private Drone findDroneWithLoadedMedications(String droneSerialNumber) {
        return droneRepository.findBySerialNumberWithLoadedMedications(droneSerialNumber)
                .orElseThrow(() -> new DroneDetailsException("drone not found"));
    }
}
