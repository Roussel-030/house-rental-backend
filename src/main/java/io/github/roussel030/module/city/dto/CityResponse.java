package io.github.roussel030.module.city.dto;

import io.github.roussel030.module.country.dto.CountryResponse;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Schema(
        name = "CityResponse",
        description = "Response returned for a city including its associated country"
)
public record CityResponse(

        @Schema(
                description = "Unique ID of the city",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Name of the city",
                example = "Paris"
        )
        String name,

        @Schema(
                description = "Country where the city is located"
        )
        CountryResponse country

) {}