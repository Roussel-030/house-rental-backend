package io.github.roussel030.user.dto;

import io.github.roussel030.user.enumeration.Role;
import io.github.roussel030.user.enumeration.UserStatus;
import lombok.Builder;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Builder
@Schema(
        name = "UserResponse",
        description = "Response returned for a user"
)
public record UserResponse(

        @Schema(description = "Unique ID of the user", example = "1")
        Long id,

        @Schema(description = "First name of the user", example = "John")
        String firstName,

        @Schema(description = "Last name of the user", example = "Doe")
        String lastName,

        @Schema(description = "Email address of the user", example = "john.doe@example.com")
        String email,

        @Schema(description = "Role assigned to the user", example = "ADMIN")
        Role role,

        @Schema(description = "Current account status", example = "ACTIVE")
        UserStatus status
) {}