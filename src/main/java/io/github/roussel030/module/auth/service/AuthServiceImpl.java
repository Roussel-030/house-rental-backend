package io.github.roussel030.module.auth.service;

import io.github.roussel030.module.auth.dto.AuthRequest;
import io.github.roussel030.module.auth.dto.AuthResponse;
import io.github.roussel030.module.auth.exception.InvalidCredentialsException;
import io.github.roussel030.module.auth.service.jwtService.JwtService;
import io.github.roussel030.module.user.entity.User;
import io.github.roussel030.module.user.enumeration.UserStatus;
import io.github.roussel030.module.user.exception.UserNotActivatedException;
import io.github.roussel030.module.user.exception.UserNotFoundException;
import io.github.roussel030.module.user.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final SecurityIdentity securityIdentity;

    @Inject
    public AuthServiceImpl(UserRepository userRepository, JwtService jwtService, SecurityIdentity securityIdentity) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.securityIdentity = securityIdentity;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        User user = userRepository.findByEmail(request.username())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

        if (!BcryptUtil.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new UserNotActivatedException("User account is not activated. Please check your email.");
        }

        String token = jwtService.generateToken(user);

        return AuthResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .token(token)
                .build();
    }

    @Override
    public AuthResponse getCurrentUserInfo() {
        String email = securityIdentity.getPrincipal().getName(); // Use securityIdentity
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        return AuthResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}
