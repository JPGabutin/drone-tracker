package com.jpg.drone_tracker.application.component.drone.api.controller;

import com.jpg.drone_tracker.application.component.drone.api.dto.DroneResponse;
import com.jpg.drone_tracker.application.component.drone.api.dto.RegisterDroneRequest;
import com.jpg.drone_tracker.application.component.drone.service.RegisterDroneCommand;
import com.jpg.drone_tracker.application.component.drone.service.DroneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drones")
public class DroneController {

    private final DroneService droneService;

    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DroneResponse registerDrone(@Valid @RequestBody RegisterDroneRequest request) {
        var savedDrone = droneService.registerDrone(new RegisterDroneCommand(
                request.serialNumber(),
                request.model()
        ));

        return new DroneResponse(
                savedDrone.getId(),
                savedDrone.getSerialNumber(),
                savedDrone.getModel(),
                savedDrone.getWeightLimit(),
                savedDrone.getCurrentLoadWeight(),
                savedDrone.getBatteryCapacity(),
                savedDrone.getState()
        );
    }
}
