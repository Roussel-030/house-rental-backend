package io.github.roussel030.user.dto;

import io.github.roussel030.user.enumeration.Role;
import io.github.roussel030.user.enumeration.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserAdminRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        String lastName,

        @NotBlank
        @Email(message = "Invalid email")
        String email,

        @NotNull
        Role role,

        @NotNull
        UserStatus status
) {
}
