package io.github.roussel030.module.country.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "CountryRequest",
        description = "Request payload to create or update a country"
)
public record CountryRequest(

        @Schema(
                description = "Name of the country",
                example = "United States",
                required = true
        )
        @NotBlank(message = "Country name is required")
        @Size(min = 2, message = "Country name must be at least 2 characters long")
        String name,

        @Schema(
                description = "ID of the currency associated with the country",
                example = "1",
                required = true
        )
        @NotNull(message = "Currency ID is required")
        Long currencyId

) {}