package io.github.roussel030.module.country.dto;

import io.github.roussel030.module.currency.dto.CurrencyResponse;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Schema(
        name = "CountryResponse",
        description = "Response returned for a country including its associated currency"
)
public record CountryResponse(

        @Schema(
                description = "Unique ID of the country",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Name of the country",
                example = "United States"
        )
        String name,

        @Schema(
                description = "Currency associated with the country"
        )
        CurrencyResponse currency

) {}