package com.jpg.drone_tracker.application.component.drone.api.controller;

import com.jpg.drone_tracker.application.component.drone.api.dto.DroneResponse;
import com.jpg.drone_tracker.application.component.drone.api.dto.LoadDroneMedicationRequest;
import com.jpg.drone_tracker.application.component.drone.domain.Drone;
import com.jpg.drone_tracker.application.component.drone.service.DroneLoadingService;
import com.jpg.drone_tracker.application.component.drone.service.LoadDroneMedicationCommand;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drones")
public class DroneLoadingController {

    private final DroneLoadingService droneLoadingService;

    public DroneLoadingController(DroneLoadingService droneLoadingService) {
        this.droneLoadingService = droneLoadingService;
    }

    @PostMapping("/{serialNumber}/loading")
    @ResponseStatus(HttpStatus.OK)
    public DroneResponse startLoading(
            @PathVariable String serialNumber,
            @Valid @RequestBody LoadDroneMedicationRequest request
    ) {
        Drone drone = droneLoadingService.startLoading(new LoadDroneMedicationCommand(
                serialNumber,
                request.medicationCode()
        ));

        return new DroneResponse(
                drone.getId(),
                drone.getSerialNumber(),
                drone.getModel(),
                drone.getWeightLimit(),
                drone.getCurrentLoadWeight(),
                drone.getBatteryCapacity(),
                drone.getState()
        );
    }

    @PostMapping("/{serialNumber}/loading/complete")
    @ResponseStatus(HttpStatus.OK)
    public DroneResponse completeLoading(@PathVariable String serialNumber) {
        Drone drone = droneLoadingService.completeLoading(serialNumber);

        return new DroneResponse(
                drone.getId(),
                drone.getSerialNumber(),
                drone.getModel(),
                drone.getWeightLimit(),
                drone.getCurrentLoadWeight(),
                drone.getBatteryCapacity(),
                drone.getState()
        );
    }
}
