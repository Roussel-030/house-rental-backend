package io.github.roussel030.module.option.dto;

import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Schema(name = "OptionResponse", description = "Response returned for a house option")
public record OptionResponse(

        @Schema(description = "Unique ID of the house option", example = "1")
        Long id,

        @Schema(description = "Name of the house option", example = "Garage")
        String name,

        @Schema(description = "Icon representing the option", example = "fa-solid fa-car")
        String icon

) {}