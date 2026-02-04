package io.github.roussel030.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        String lastName,

        @NotBlank
        @Email(message = "Invalid email")
        String email
) {
}
