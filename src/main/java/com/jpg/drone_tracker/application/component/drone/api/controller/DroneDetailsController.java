package com.jpg.drone_tracker.application.component.drone.api.controller;

import com.jpg.drone_tracker.application.component.drone.api.dto.DroneAvailabilityResponse;
import com.jpg.drone_tracker.application.component.drone.api.dto.DroneBatteryResponse;
import com.jpg.drone_tracker.application.component.drone.api.dto.LoadedMedicationResponse;
import com.jpg.drone_tracker.application.component.drone.service.DroneDetailsService;
import com.jpg.drone_tracker.application.component.medication.domain.Medication;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/drones")
public class DroneDetailsController {

    private final DroneDetailsService droneDetailsService;

    public DroneDetailsController(DroneDetailsService droneDetailsService) {
        this.droneDetailsService = droneDetailsService;
    }

    @GetMapping("/{serialNumber}/medications")
    @ResponseStatus(HttpStatus.OK)
    public List<LoadedMedicationResponse> getLoadedMedications(@PathVariable @NotBlank String serialNumber) {
        return droneDetailsService.getLoadedMedications(serialNumber)
                .stream()
                .map(DroneDetailsController::toLoadedMedicationResponse)
                .toList();
    }

    @GetMapping("/{serialNumber}/availability")
    @ResponseStatus(HttpStatus.OK)
    public DroneAvailabilityResponse getAvailability(@PathVariable @NotBlank String serialNumber) {
        return new DroneAvailabilityResponse(
                serialNumber,
                droneDetailsService.isAvailableForLoading(serialNumber)
        );
    }

    @GetMapping("/{serialNumber}/battery")
    @ResponseStatus(HttpStatus.OK)
    public DroneBatteryResponse getBattery(@PathVariable @NotBlank String serialNumber) {
        return new DroneBatteryResponse(
                serialNumber,
                droneDetailsService.getBatteryCapacity(serialNumber)
        );
    }

    private static LoadedMedicationResponse toLoadedMedicationResponse(Medication medication) {
        return new LoadedMedicationResponse(
                medication.getId(),
                medication.getName(),
                medication.getWeight(),
                medication.getCode(),
                medication.getImage()
        );
    }
}
