package io.github.roussel030.module.user.dto;

import io.github.roussel030.module.user.enumeration.Role;
import io.github.roussel030.module.user.enumeration.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "UserAdminUpdateRequest",
        description = "Request payload used by an administrator to update a user with role and status"
)
public record UserAdminUpdateRequest(

        @Schema(description = "First name of the user", example = "John", required = true)
        @NotBlank(message = "First name is required")
        String firstName,

        @Schema(description = "Last name of the user", example = "Doe")
        String lastName,

        @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        String email,

        @Schema(description = "Password of the user. Optional for update", example = "MySecurePass123", required = false)
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        String password,

        @Schema(description = "Role assigned to the user", example = "ADMIN", required = true)
        @NotNull(message = "Role is required")
        Role role,

        @Schema(description = "Current status of the user account", example = "ACTIVE", required = true)
        @NotNull(message = "Status is required")
        UserStatus status

) {}