package io.github.roussel030.module.neighborhood.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NeighborhoodRequest(

        @NotBlank(message = "Neighborhood name is required")
        String name,

        @NotNull(message = "City ID is required")
        Long cityId

) {}