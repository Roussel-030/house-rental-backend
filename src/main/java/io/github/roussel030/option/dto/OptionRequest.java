package io.github.roussel030.option.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(name = "OptionRequest", description = "Request payload to create or update a house option (e.g., Garage, Swimming Pool, Balcony)")
public record OptionRequest(

        @Schema(
                description = "Name of the house option",
                example = "Garage",
                required = true
        )
        @NotBlank(message = "Option name is required")
        @Size(min = 3, max = 200, message = "Option name must be between 3 and 200 characters long")
        String name,

        @Schema(
                description = "Icon representing the option (e.g., FontAwesome class or emoji)",
                example = "fa-solid fa-car",
                required = true
        )
        @NotBlank(message = "Option icon is required")
        @Size(min = 2, max = 200, message = "Icon must be between 2 and 100 characters long")
        String icon

) {}
