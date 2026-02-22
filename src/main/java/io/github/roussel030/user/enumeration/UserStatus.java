package io.github.roussel030.user.enumeration;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "UserStatus",
        description = "Current status of the user account"
)
public enum UserStatus {

    @Schema(description = "User account is active and can access the system")
    ACTIVE,

    @Schema(description = "User account is disabled and cannot access the system")
    INACTIVE

}