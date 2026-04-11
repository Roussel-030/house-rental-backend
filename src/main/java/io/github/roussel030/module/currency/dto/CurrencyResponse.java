package io.github.roussel030.module.currency.dto;

import lombok.Builder;

@Builder
public record CurrencyResponse(
        Long id,
        String code,
        String name,
        String symbol
) {}