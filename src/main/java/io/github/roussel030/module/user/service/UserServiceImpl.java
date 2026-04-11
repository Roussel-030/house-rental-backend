package io.github.roussel030.module.user.service;

import io.github.roussel030.module.activationToken.repository.ActivationTokenRepository;
import io.github.roussel030.module.activationToken.service.ActivationTokenService;
import io.github.roussel030.module.user.dto.UserAdminUpdateRequest;
import io.github.roussel030.shared.dto.PageResponse;
import io.github.roussel030.module.user.dto.UserAdminCreateRequest;
import io.github.roussel030.module.user.dto.UserRequest;
import io.github.roussel030.module.user.dto.UserResponse;
import io.github.roussel030.module.user.entity.User;
import io.github.roussel030.module.user.exception.UserAlreadyExistsException;
import io.github.roussel030.module.user.exception.UserNotFoundException;
import io.github.roussel030.module.user.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ActivationTokenService activationTokenService;
    private final ActivationTokenRepository activationTokenRepository;

    public UserServiceImpl(
            UserRepository userRepository,
            ActivationTokenService activationTokenService,
            ActivationTokenRepository activationTokenRepository
    ) {
        this.userRepository = userRepository;
        this.activationTokenService = activationTokenService;
        this.activationTokenRepository = activationTokenRepository;
    }

    @Override
    @Transactional
    public UserResponse createUserAsUser(UserRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(BcryptUtil.bcryptHash(request.password()));
        userRepository.save(user);

        String rawToken = activationTokenService.createActivationToken(user);

        // TODO

        return mapToResponse(user);
    }

    @Override
    @Transactional
    public UserResponse createUserAsAdmin(UserAdminCreateRequest request) {
        if(userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(BcryptUtil.bcryptHash(request.password()));
        user.setRole(request.role());
        user.setStatus(request.status());
        userRepository.save(user);

        return mapToResponse(user);
    }

    @Override
    public PageResponse<UserResponse> getUsers(String search, int page, int size) {
        List<UserResponse> users = userRepository.findAllPaginated(search, page, size)
                .stream()
                .map(user -> mapToResponse(user))
                .toList();

        long total = getCountTotalUser(search);

        return PageResponse.<UserResponse>builder()
                .items(users)
                .page(page)
                .size(size)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public UserResponse updateUserAsAdmin(Long id, UserAdminUpdateRequest request) {
        User user = userRepository.findByIdOptional(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+ id));

        if(!user.getEmail().equals(request.email()) &&
                userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setRole(request.role());
        user.setStatus(request.status());

        if(request.password() != null && !request.password().isBlank()) {
            user.setPassword(BcryptUtil.bcryptHash(request.password()));
        }

        userRepository.update(user);

        return mapToResponse(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findByIdOptional(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: "+id));

        activationTokenRepository.deleteTokensWithUser(user.getId());
        userRepository.deleteUser(user);

    }

    private Long getCountTotalUser(String search) {
        return userRepository.countALl(search);
    }

    private UserResponse mapToResponse(User user) {
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
