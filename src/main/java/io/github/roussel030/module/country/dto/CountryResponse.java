package io.github.roussel030.module.country.dto;

import io.github.roussel030.module.currency.dto.CurrencyResponse;
import lombok.Builder;

@Builder
public record CountryResponse(
        Long id,
        String name,
        CurrencyResponse currency
) {}