package io.github.roussel030.module.auth.service.jwtService;

import io.github.roussel030.module.user.entity.User;

public interface JwtService {
    String generateToken(User user);
}
