package io.github.roussel030.module.country.dto;

public record CountryRequest(
        Long id,
        String name,
        Long currencyId
) {}
