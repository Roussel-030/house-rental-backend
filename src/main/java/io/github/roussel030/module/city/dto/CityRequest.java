package io.github.roussel030.module.city.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "CityRequest",
        description = "Request payload to create or update a city"
)
public record CityRequest(

        @Schema(
                description = "Name of the city",
                example = "Paris",
                required = true
        )
        @NotBlank(message = "City name is required")
        String name,

        @Schema(
                description = "ID of the country where the city is located",
                example = "1",
                required = true
        )
        @NotNull(message = "Country ID is required")
        Long countryId

) {}