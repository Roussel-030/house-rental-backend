package io.github.roussel030.module.auth.dto;

import io.github.roussel030.module.user.enumeration.Role;
import io.github.roussel030.module.user.enumeration.UserStatus;
import lombok.Builder;

@Builder
public record AuthResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role,
        UserStatus status,
        String token
) {}
