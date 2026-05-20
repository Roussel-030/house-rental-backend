package io.github.roussel030.module.activationToken.service;

import io.github.roussel030.module.activationToken.entity.ActivationToken;
import io.github.roussel030.module.activationToken.exception.ActivationTokenInvalidException;
import io.github.roussel030.module.activationToken.repository.ActivationTokenRepository;
import io.github.roussel030.module.user.dto.UserResponse;
import io.github.roussel030.module.user.entity.User;
import io.github.roussel030.module.user.exception.UserNotFoundException;
import io.github.roussel030.module.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActivationTokenServiceImplTest {

    @Mock
    private ActivationTokenRepository activationTokenRepository;

    @Mock
    private UserRepository userRepository;

    private ActivationTokenServiceImpl activationTokenService;

    private final int expirationHours = 24;

    @BeforeEach
    void setUp() {
        activationTokenService = new ActivationTokenServiceImpl(activationTokenRepository, userRepository, expirationHours);
    }

    @Test
    void createActivationToken_Success() {
        User user = new User();
        user.setId(1L);

        String rawToken = activationTokenService.createActivationToken(user);

        assertNotNull(rawToken);
        verify(activationTokenRepository).invalidateUserTokens(1L);
        verify(activationTokenRepository).createToken(any(ActivationToken.class));
    }

    @Test
    void validateActivationToken_Success() {
        String rawToken = "valid-token";
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");

        ActivationToken token = new ActivationToken();
        token.setId(1L);
        token.setUser(user);

        when(activationTokenRepository.findValidToken(anyString())).thenReturn(Optional.of(token));
        when(userRepository.findByIdOptional(1L)).thenReturn(Optional.of(user));

        UserResponse response = activationTokenService.validateActivationToken(rawToken);

        assertNotNull(response);
        assertEquals(user.getId(), response.id());
        assertEquals(user.getEmail(), response.email());
        verify(activationTokenRepository).markAsUsed(1L);
        verify(userRepository).activeUser(1L);
    }

    @Test
    void validateActivationToken_InvalidToken_ThrowsException() {
        String rawToken = "invalid-token";
        when(activationTokenRepository.findValidToken(anyString())).thenReturn(Optional.empty());

        assertThrows(ActivationTokenInvalidException.class, () -> activationTokenService.validateActivationToken(rawToken));
    }

    @Test
    void validateActivationToken_UserNotFound_ThrowsException() {
        String rawToken = "valid-token";
        User user = new User();
        user.setId(1L);

        ActivationToken token = new ActivationToken();
        token.setId(1L);
        token.setUser(user);

        when(activationTokenRepository.findValidToken(anyString())).thenReturn(Optional.of(token));
        when(userRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> activationTokenService.validateActivationToken(rawToken));
    }
}
