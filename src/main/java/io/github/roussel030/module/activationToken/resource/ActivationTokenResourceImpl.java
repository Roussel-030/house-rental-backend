package io.github.roussel030.module.activationToken.resource;

import io.github.roussel030.module.activationToken.service.ActivationTokenService;
import io.github.roussel030.module.user.dto.UserResponse;
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
        UserResponse response = activationTokenService.validateActivationToken(rawToken);
        return Response.ok(response).build();
    }

}
