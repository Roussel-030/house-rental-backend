package io.github.roussel030.activationToken.service;

import io.github.roussel030.user.dto.UserResponse;
import io.github.roussel030.user.entity.User;

public interface ActivationTokenService {

    String createToken(User user);
    UserResponse validateToken(String rawToken);

}
