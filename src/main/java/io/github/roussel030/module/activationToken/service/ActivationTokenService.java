package io.github.roussel030.module.activationToken.service;

import io.github.roussel030.module.user.dto.UserResponse;
import io.github.roussel030.module.user.entity.User;

public interface ActivationTokenService {

    String createActivationToken(User user);
    UserResponse validateActivationToken(String rawToken);

}
