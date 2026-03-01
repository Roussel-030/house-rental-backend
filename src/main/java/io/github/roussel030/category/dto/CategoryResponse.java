package io.github.roussel030.category.dto;

import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Schema(name = "CategoryResponse", description = "Response returned for a house category")
public record CategoryResponse(

        @Schema(description = "Unique ID of the house category", example = "1")
        Long id,

        @Schema(description = "Name of the house category", example = "Living Room")
        String name

) {}
