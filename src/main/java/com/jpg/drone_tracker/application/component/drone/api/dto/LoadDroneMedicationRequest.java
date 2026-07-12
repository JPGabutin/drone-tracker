package com.jpg.drone_tracker.application.component.drone.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoadDroneMedicationRequest(
        @NotBlank
        @Size(max = 100)
        @Pattern(regexp = "^[A-Z0-9_]+$")
        String medicationCode
) {
}
