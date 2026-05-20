package io.github.roussel030.module.auth.service;

import io.github.roussel030.module.auth.dto.AuthRequest;
import io.github.roussel030.module.auth.dto.AuthResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest request);
    AuthResponse getCurrentUserInfo();
}
