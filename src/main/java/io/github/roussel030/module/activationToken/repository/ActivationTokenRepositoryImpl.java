package io.github.roussel030.module.activationToken.repository;

import io.github.roussel030.module.activationToken.entity.ActivationToken;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class ActivationTokenRepositoryImpl implements ActivationTokenRepository, PanacheRepository<ActivationToken> {

    @Override
    public void createToken(ActivationToken activationToken) {
        persist(activationToken);
    }

    @Override
    public Optional<ActivationToken> findValidToken(String hash) {
        return find(
                "tokenHash = ?1 and used = false and expiresAt > ?2",
                hash,
                LocalDateTime.now()
        ).firstResultOptional();
    }

    @Override
    public void deleteExpired() {
        delete("expiresAt < ?1", LocalDateTime.now());
    }

    @Override
    public void invalidateUserTokens(Long userId) {
        update("used = true where user.id = ?1", userId);
    }

    @Override
    public void markAsUsed(Long tokenId) {
        update("used = true where id = ?1", tokenId);
    }

    @Override
    public void deleteTokensWithUser(Long userId) {
        delete("user.id = ?1", userId);
    }

}
