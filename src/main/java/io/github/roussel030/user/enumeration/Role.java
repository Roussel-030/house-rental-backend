package io.github.roussel030.user.enumeration;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(
        name = "Role",
        description = "Role assigned to a user in the system"
)
public enum Role {

    @Schema(description = "Administrator with full access")
    ADMIN,

    @Schema(description = "Standard user with limited permissions")
    USER

}