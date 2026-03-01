package io.github.roussel030.currency.dto;

import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Schema(name = "CurrencyResponse", description = "Response returned for a currency")
public record CurrencyResponse(

        @Schema(description = "Unique ID of the currency", example = "1")
        Long id,

        @Schema(description = "ISO 4217 currency code", example = "USD")
        String code,

        @Schema(description = "Full name of the currency", example = "United States Dollar")
        String name,

        @Schema(description = "Symbol of the currency", example = "$")
        String symbol

) {}