package io.github.roussel030.module.country.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CountryRequest(

        @NotBlank(message = "Country name is required")
        @Size(min = 2, message = "Country name must be at least 2 characters long")
        String name,

        @NotNull(message = "Currency ID is required")
        Long currencyId

) {}