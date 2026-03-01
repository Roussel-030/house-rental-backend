package io.github.roussel030.module.user.dto;

import io.github.roussel030.module.user.enumeration.Role;
import io.github.roussel030.module.user.enumeration.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "UserAdminRequest",
        description = "Request payload used by an administrator to create or update a user with role and status"
)
public record UserAdminRequest(

        @Schema(description = "First name of the user", example = "John", required = true)
        @NotBlank(message = "First name is required")
        String firstName,

        @Schema(description = "Last name of the user", example = "Doe")
        String lastName,

        @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
        @NotBlank
        @Email(message = "Invalid email")
        String email,

        @Schema(description = "Role assigned to the user", example = "ADMIN", required = true)
        @NotNull
        Role role,

        @Schema(description = "Current status of the user account", example = "ACTIVE", required = true)
        @NotNull
        UserStatus status

) {}