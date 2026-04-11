package io.github.roussel030.module.category.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Long id,
        String name
) {}
