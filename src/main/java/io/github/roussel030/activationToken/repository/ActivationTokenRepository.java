package io.github.roussel030.activationToken.repository;

import io.github.roussel030.activationToken.entity.ActivationToken;

import java.util.Optional;

public interface ActivationTokenRepository {

    void createToken(ActivationToken activationToken);
    Optional<ActivationToken> findValidToken(String hash);
    void deleteExpired();
    void invalidateUserTokens(Long userId);

}
