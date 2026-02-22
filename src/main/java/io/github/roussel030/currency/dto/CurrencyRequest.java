package io.github.roussel030.currency.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CurrencyRequest", description = "Request payload to create or update a currency for a property's country")
public record CurrencyRequest(

        @Schema(description = "ISO 4217 currency code", example = "USD", required = true)
        @NotBlank(message = "Currency code is required")
        @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters (ISO 4217)")
        String code,

        @Schema(description = "Full name of the currency", example = "United States Dollar", required = true)
        @NotBlank(message = "Currency name is required")
        @Size(min = 3, message = "Currency name must be at least 3 characters long")
        String name,

        @Schema(description = "Symbol of the currency", example = "$", required = true)
        @NotBlank(message = "Currency symbol is required")
        @Size(min = 1, max = 5, message = "Currency symbol must be between 1 and 5 characters")
        String symbol

) {}