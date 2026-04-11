package io.github.roussel030.module.city.dto;

import io.github.roussel030.module.country.dto.CountryResponse;
import lombok.Builder;

@Builder
public record CityResponse(
        Long id,
        String name,
        CountryResponse country
) {}