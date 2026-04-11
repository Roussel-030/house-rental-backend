package io.github.roussel030.module.neighborhood.dto;

import io.github.roussel030.module.city.dto.CityResponse;
import lombok.Builder;

@Builder
public record NeighborhoodResponse(
        Long id,
        String name,
        CityResponse city
) {}