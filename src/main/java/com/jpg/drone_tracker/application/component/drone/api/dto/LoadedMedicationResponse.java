package com.jpg.drone_tracker.application.component.drone.api.dto;

import java.util.UUID;

public record LoadedMedicationResponse(
        UUID id,
        String name,
        int weight,
        String code,
        String image
) {
}
