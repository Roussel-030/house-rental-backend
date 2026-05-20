package io.github.roussel030.module.activationToken.resource;

import io.github.roussel030.module.activationToken.service.ActivationTokenService;
import io.github.roussel030.module.user.dto.UserResponse;
import io.github.roussel030.module.user.enumeration.Role;
import io.github.roussel030.module.user.enumeration.UserStatus;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActivationTokenResourceImplTest {

    @Mock
    private ActivationTokenService activationTokenService;

    private ActivationTokenResourceImpl activationTokenResource;

    @BeforeEach
    void setUp() {
        activationTokenResource = new ActivationTokenResourceImpl(activationTokenService);
    }

    @Test
    void activate_ShouldReturnOkResponseWithUserResponse() {
        // Arrange
        String rawToken = "test-token";
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .role(Role.USER)
                .status(UserStatus.ACTIVE)
                .build();
        when(activationTokenService.validateActivationToken(rawToken)).thenReturn(userResponse);

        // Act
        Response response = activationTokenResource.activate(rawToken);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(userResponse, response.getEntity());
        verify(activationTokenService).validateActivationToken(rawToken);
    }
}
