package io.github.roussel030.module.user.service;

import io.github.roussel030.module.activationToken.repository.ActivationTokenRepository;
import io.github.roussel030.module.activationToken.service.ActivationTokenService;
import io.github.roussel030.module.user.dto.*;
import io.github.roussel030.module.user.entity.User;
import io.github.roussel030.module.user.enumeration.Role;
import io.github.roussel030.module.user.enumeration.UserStatus;
import io.github.roussel030.module.user.exception.UserAlreadyExistsException;
import io.github.roussel030.module.user.exception.UserNotFoundException;
import io.github.roussel030.module.user.repository.UserRepository;
import io.github.roussel030.shared.dto.PageResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ActivationTokenService activationTokenService;

    @Mock
    private ActivationTokenRepository activationTokenRepository;

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, activationTokenService, activationTokenRepository);
    }

    @Test
    void createUserAsUser_Success() {
        UserRequest request = new UserRequest("John", "Doe", "john.doe@example.com", "password");
        when(userRepository.existsByEmail(request.email())).thenReturn(false);
        when(activationTokenService.createActivationToken(any(User.class))).thenReturn("token");

        try (MockedStatic<BcryptUtil> bcryptUtil = mockStatic(BcryptUtil.class)) {
            bcryptUtil.when(() -> BcryptUtil.bcryptHash(anyString())).thenReturn("hashed_password");

            UserResponse response = userService.createUserAsUser(request);

            assertNotNull(response);
            assertEquals(request.email(), response.email());
            verify(userRepository).save(any(User.class));
            verify(activationTokenService).createActivationToken(any(User.class));
        }
    }

    @Test
    void createUserAsUser_AlreadyExists_ThrowsException() {
        UserRequest request = new UserRequest("John", "Doe", "john.doe@example.com", "password");
        when(userRepository.existsByEmail(request.email())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUserAsUser(request));
    }

    @Test
    void createUserAsAdmin_Success() {
        UserAdminCreateRequest request = new UserAdminCreateRequest("Admin", "User", "admin@example.com", "password", Role.ADMIN, UserStatus.ACTIVE);
        when(userRepository.existsByEmail(request.email())).thenReturn(false);

        try (MockedStatic<BcryptUtil> bcryptUtil = mockStatic(BcryptUtil.class)) {
            bcryptUtil.when(() -> BcryptUtil.bcryptHash(anyString())).thenReturn("hashed_password");

            UserResponse response = userService.createUserAsAdmin(request);

            assertNotNull(response);
            assertEquals(request.email(), response.email());
            assertEquals(Role.ADMIN, response.role());
            verify(userRepository).save(any(User.class));
        }
    }

    @Test
    void getUsers_Success() {
        String search = "";
        int page = 0;
        int size = 10;
        User user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");

        when(userRepository.findAllPaginated(search, page, size)).thenReturn(List.of(user));
        when(userRepository.countALl(search)).thenReturn(1L);

        PageResponse<UserResponse> response = userService.getUsers(search, page, size);

        assertNotNull(response);
        assertEquals(1, response.items().size());
        assertEquals(1, response.total());
    }

    @Test
    void updateUserAsAdmin_Success() {
        Long id = 1L;
        UserAdminUpdateRequest request = new UserAdminUpdateRequest("John", "Updated", "john.updated@example.com", "new_password", Role.USER, UserStatus.ACTIVE);
        User user = new User();
        user.setId(id);
        user.setEmail("john.doe@example.com");

        when(userRepository.findByIdOptional(id)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(request.email())).thenReturn(false);

        try (MockedStatic<BcryptUtil> bcryptUtil = mockStatic(BcryptUtil.class)) {
            bcryptUtil.when(() -> BcryptUtil.bcryptHash(anyString())).thenReturn("hashed_password");

            UserResponse response = userService.updateUserAsAdmin(id, request);

            assertNotNull(response);
            assertEquals(request.email(), response.email());
            verify(userRepository).update(user);
        }
    }

    @Test
    void deleteUser_Success() {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepository.findByIdOptional(id)).thenReturn(Optional.of(user));

        userService.deleteUser(id);

        verify(activationTokenRepository).deleteTokensWithUser(id);
        verify(userRepository).deleteUser(user);
    }

    @Test
    void deleteUser_NotFound_ThrowsException() {
        Long id = 1L;
        when(userRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(id));
    }
}
