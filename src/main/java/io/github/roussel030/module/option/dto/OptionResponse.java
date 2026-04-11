package io.github.roussel030.module.option.dto;

import lombok.Builder;

@Builder
public record OptionResponse(
        Long id,
        String name,
        String icon
) {}