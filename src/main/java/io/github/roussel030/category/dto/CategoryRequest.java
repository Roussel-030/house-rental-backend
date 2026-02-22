package io.github.roussel030.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "CategoryRequest", description = "Request payload to create or update a house category")
public record CategoryRequest(

        @Schema(
                description = "Name of the house category",
                example = "Living Room",
                required = true
        )
        @NotBlank(message = "Category name is required")
        @Size(min = 3, message = "Category name must be at least 3 characters long")
        String name

) {}
