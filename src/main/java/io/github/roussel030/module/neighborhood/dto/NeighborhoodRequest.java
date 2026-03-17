package io.github.roussel030.module.neighborhood.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "NeighborhoodRequest",
        description = "Request payload to create or update a neighborhood where a property is located"
)
public record NeighborhoodRequest(

        @Schema(
                description = "Name of the neighborhood (area/district within a city)",
                example = "Manhattan",
                required = true
        )
        @NotBlank(message = "Neighborhood name is required")
        String name,

        @Schema(
                description = "ID of the city where the neighborhood is located",
                example = "1",
                required = true
        )
        @NotNull(message = "City ID is required")
        Long cityId

) {}