package io.github.roussel030.option.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OptionRequest(

        @NotBlank(message = "Option name is required")
        @Size(min = 3, max = 200, message = "Option name must be between 3 and 200 characters long")
        String name,

        @NotBlank(message = "Option icon is required")
        @Size(min = 2, max = 200, message = "Icon must be between 2 and 100 characters long")
        String icon

) {}
