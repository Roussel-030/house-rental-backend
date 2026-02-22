package io.github.roussel030.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
        String email

) {}