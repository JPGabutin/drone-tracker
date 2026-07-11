package com.jpg.drone_tracker.application.component.drone.api.dto;

import com.jpg.drone_tracker.application.component.drone.domain.DroneModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterDroneRequest(
        @NotBlank
        @Size(max = 100)
        String serialNumber,

        @NotNull
        DroneModel model
) {
}
