package io.github.roussel030.module.neighborhood.dto;

import io.github.roussel030.module.city.dto.CityResponse;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Schema(
        name = "NeighborhoodResponse",
        description = "Response returned for a neighborhood including its associated city"
)
public record NeighborhoodResponse(

        @Schema(
                description = "Unique ID of the neighborhood",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Name of the neighborhood",
                example = "Manhattan"
        )
        String name,

        @Schema(
                description = "City where the neighborhood is located"
        )
        CityResponse city

) {}