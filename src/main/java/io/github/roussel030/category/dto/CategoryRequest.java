package io.github.roussel030.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Category name is required")
        @Size(min = 3, message = "Category name must be at least 3 characters long")
        String name
) {}
