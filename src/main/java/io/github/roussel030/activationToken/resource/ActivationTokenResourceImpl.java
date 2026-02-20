package io.github.roussel030.activationToken.resource;

import io.github.roussel030.activationToken.service.ActivationTokenService;
import io.github.roussel030.user.dto.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ActivationTokenResourceImpl implements ActivationTokenResource {

    private final ActivationTokenService activationTokenService;

    public ActivationTokenResourceImpl(ActivationTokenService activationTokenService) {
        this.activationTokenService = activationTokenService;
    }

    @Override
    public Response activate(String rawToken) {
        UserResponse response = activationTokenService.validateToken(rawToken);
        return Response.ok(response).build();
    }

}
