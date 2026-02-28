package io.github.roussel030.activationToken.service;

import io.github.roussel030.activationToken.entity.ActivationToken;
import io.github.roussel030.activationToken.exception.ActivationTokenInvalidException;
import io.github.roussel030.activationToken.repository.ActivationTokenRepository;
import io.github.roussel030.user.dto.UserResponse;
import io.github.roussel030.user.entity.User;
import io.github.roussel030.user.exception.UserNotFoundException;
import io.github.roussel030.user.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@ApplicationScoped
public class ActivationTokenServiceImpl implements ActivationTokenService {

    private final ActivationTokenRepository activationTokenRepository;
    private final UserRepository userRepository;
    private static final SecureRandom random = new SecureRandom();
    private final int expirationHours;

    public ActivationTokenServiceImpl(
            ActivationTokenRepository activationTokenRepository,
            UserRepository userRepository,
            @ConfigProperty(name = "activation-token.expiration-hours") int expirationHours
    ) {
        this.activationTokenRepository = activationTokenRepository;
        this.userRepository = userRepository;
        this.expirationHours = expirationHours;
    }

    @Override
    public String createActivationToken(User user) {
        activationTokenRepository.invalidateUserTokens(user.getId());

        String rawToken = generateSecureToken();
        String hash = sha256(rawToken);

        ActivationToken activationToken = new ActivationToken();
        activationToken.setUser(user);
        activationToken.setTokenHash(hash);
        activationToken.setExpiresAt(LocalDateTime.now().plusHours(expirationHours));
        activationTokenRepository.createToken(activationToken);

        return rawToken;
    }

    @Override
    @Transactional
    public UserResponse validateActivationToken(String rawToken) {
        String hash = sha256(rawToken);

        ActivationToken activationToken = activationTokenRepository.findValidToken(hash).orElseThrow(
                () -> new ActivationTokenInvalidException("Invalid token")
        );

        Long tokenId = activationToken.getId();
        Long userId = activationToken.getUser().getId();

        activationTokenRepository.markAsUsed(tokenId);
        userRepository.activeUser(userId);

        User user = getUser(userId);

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }

    private User getUser(Long userId) {
        return userRepository.findByIdOptional(userId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + userId)
        );
    }

    private String generateSecureToken() {
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String sha256(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(md.digest(input.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
