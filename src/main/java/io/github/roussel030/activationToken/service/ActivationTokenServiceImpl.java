package io.github.roussel030.activationToken.service;

import io.github.roussel030.activationToken.entity.ActivationToken;
import io.github.roussel030.activationToken.exception.ActivationTokenInvalidException;
import io.github.roussel030.activationToken.repository.ActivationTokenRepositoryImpl;
import io.github.roussel030.user.dto.UserResponse;
import io.github.roussel030.user.entity.User;
import io.github.roussel030.user.exception.UserNotFoundException;
import io.github.roussel030.user.repository.UserRepositoryImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@ApplicationScoped
public class ActivationTokenServiceImpl implements ActivationTokenService {

    private final ActivationTokenRepositoryImpl activationTokenRepository;
    private final UserRepositoryImpl userRepository;

    private static final SecureRandom random = new SecureRandom();

    private final int HOUR_TOKEN = 24;

    public ActivationTokenServiceImpl(ActivationTokenRepositoryImpl activationTokenRepository, UserRepositoryImpl userRepository) {
        this.activationTokenRepository = activationTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String createToken(User user) {
        activationTokenRepository.invalidateUserTokens(user.getId());

        String rawToken = generateSecureToken();
        String hash = sha256(rawToken);

        ActivationToken activationToken = new ActivationToken();
        activationToken.setUser(user);
        activationToken.setTokenHash(hash);
        activationToken.setExpiresAt(LocalDateTime.now().plusHours(HOUR_TOKEN));
        activationTokenRepository.createToken(activationToken);

        return rawToken;
    }

    @Override
    @Transactional
    public UserResponse validateToken(String rawToken) {
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
        return userRepository.findUserById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found")
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
