package io.github.roussel030.module.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "UserRequest",
        description = "Request payload used to register a new user"
)
public record UserRequest(

        @Schema(description = "First name of the user", example = "Jane", required = true)
        @NotBlank(message = "First name is required")
        String firstName,

        @Schema(description = "Last name of the user", example = "Smith")
        String lastName,

        @Schema(description = "Email address of the user", example = "jane.smith@example.com", required = true)
        @NotBlank
        @Email(message = "Invalid email")
        String email,

        @Schema(description = "Password of the user", example = "MySecurePass123", required = true)
        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 72, message = "Password must be between 8 and 72 characters")
        String password

) {}