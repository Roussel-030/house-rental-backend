package io.github.roussel030.module.city.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CityRequest(

        @NotBlank(message = "City name is required")
        String name,

        @NotNull(message = "Country ID is required")
        Long countryId

) {}