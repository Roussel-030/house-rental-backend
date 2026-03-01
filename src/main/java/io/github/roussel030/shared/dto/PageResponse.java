package io.github.roussel030.shared.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        List<T> items,
        int page,
        int size,
        long total
) {}
