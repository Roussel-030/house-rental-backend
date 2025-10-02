package io.github.roussel030.currency.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CurrencyRequest(
        @NotBlank(message = "Currency code is required")
        @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters (ISO 4217)")
        String code,

        @NotBlank(message = "Currency name is required")
        @Size(min = 3, message = "Currency name must be at least 3 characters long")
        String name,

        @NotBlank(message = "Currency symbol is required")
        @Size(min = 1, max = 5, message = "Currency symbol must be between 1 and 5 characters")
        String symbol
) {
}
