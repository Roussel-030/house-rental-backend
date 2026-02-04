package io.github.roussel030.user.service;

import io.github.roussel030.user.dto.UserAdminRequest;
import io.github.roussel030.user.dto.UserRequest;
import io.github.roussel030.user.dto.UserResponse;
import io.github.roussel030.user.entity.User;
import io.github.roussel030.user.exception.UserAlreadyExistsException;
import io.github.roussel030.user.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserResponse createUserAsUser(UserRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        return null;
    }

    @Override
    @Transactional
    public UserResponse createUserAsAdmin(UserAdminRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setStatus(request.status());
        userRepository.save(user);

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }

}
