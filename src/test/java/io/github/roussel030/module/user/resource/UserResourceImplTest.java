package io.github.roussel030.module.user.resource;

import io.github.roussel030.module.user.dto.UserAdminCreateRequest;
import io.github.roussel030.module.user.dto.UserAdminUpdateRequest;
import io.github.roussel030.module.user.dto.UserRequest;
import io.github.roussel030.module.user.dto.UserResponse;
import io.github.roussel030.module.user.enumeration.Role;
import io.github.roussel030.module.user.enumeration.UserStatus;
import io.github.roussel030.module.user.service.UserService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserResourceImplTest {

    @Mock
    private UserService userService;

    private UserResourceImpl userResource;

    @BeforeEach
    void setUp() {
        userResource = new UserResourceImpl(userService);
    }

    @Test
    void createUserAsUser_ShouldReturnCreated() {
        // Given
        UserRequest request = new UserRequest("John", "Doe", "john.doe@example.com", "password123");
        UserResponse response = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        when(userService.createUserAsUser(any(UserRequest.class))).thenReturn(response);

        // When
        Response result = userResource.createUserAsUser(request);

        // Then
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
        verify(userService).createUserAsUser(request);
    }

    @Test
    void createUserAsAdmin_ShouldReturnCreated() {
        // Given
        UserAdminCreateRequest request = new UserAdminCreateRequest("Admin", "User", "admin@example.com", "adminPass", Role.ADMIN, UserStatus.ACTIVE);
        UserResponse response = UserResponse.builder()
                .id(2L)
                .firstName("Admin")
                .lastName("User")
                .email("admin@example.com")
                .role(Role.ADMIN)
                .status(UserStatus.ACTIVE)
                .build();
        when(userService.createUserAsAdmin(any(UserAdminCreateRequest.class))).thenReturn(response);

        // When
        Response result = userResource.createUserAsAdmin(request);

        // Then
        assertEquals(Response.Status.CREATED.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
        verify(userService).createUserAsAdmin(request);
    }

    @Test
    void getUsers_ShouldReturnOk() {
        // Given
        PageResponse<UserResponse> pageResponse = PageResponse.<UserResponse>builder()
                .items(Collections.emptyList())
                .page(0)
                .size(10)
                .total(0)
                .build();
        when(userService.getUsers(anyString(), anyInt(), anyInt())).thenReturn(pageResponse);

        // When
        Response result = userResource.getUsers("search", 0, 10);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(pageResponse, result.getEntity());
        verify(userService).getUsers("search", 0, 10);
    }

    @Test
    void updateUserAsAdmin_ShouldReturnOk() {
        // Given
        Long id = 1L;
        UserAdminUpdateRequest request = new UserAdminUpdateRequest("John", "Updated", "john.updated@example.com", "newPassword", Role.USER, UserStatus.ACTIVE);
        UserResponse response = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Updated")
                .email("john.updated@example.com")
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        when(userService.updateUserAsAdmin(eq(id), any(UserAdminUpdateRequest.class))).thenReturn(response);

        // When
        Response result = userResource.updateUserAsAdmin(id, request);

        // Then
        assertEquals(Response.Status.OK.getStatusCode(), result.getStatus());
        assertEquals(response, result.getEntity());
        verify(userService).updateUserAsAdmin(id, request);
    }

    @Test
    void deleteUser_ShouldReturnNoContent() {
        // Given
        Long id = 1L;
        doNothing().when(userService).deleteUser(id);

        // When
        Response result = userResource.deleteUser(id);

        // Then
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), result.getStatus());
        verify(userService).deleteUser(id);
    }
}
