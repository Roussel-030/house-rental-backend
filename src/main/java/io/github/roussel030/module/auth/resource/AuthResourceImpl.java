package io.github.roussel030.module.auth.resource;

import io.github.roussel030.module.auth.dto.AuthRequest;
import io.github.roussel030.module.auth.dto.AuthResponse;
import io.github.roussel030.module.auth.service.AuthService;
import io.github.roussel030.shared.security.SecurityRoles;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import jakarta.validation.Valid;

@ApplicationScoped
public class AuthResourceImpl implements AuthResource {

    private final AuthService authService;

    public AuthResourceImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @PermitAll
    public Response login(@Valid AuthRequest request) {
        AuthResponse authResponse = authService.authenticate(request);
        return Response.ok(authResponse).build();
    }

    @Override
    @RolesAllowed({SecurityRoles.USER, SecurityRoles.ADMIN})
    public Response me() {
        AuthResponse authResponse = authService.getCurrentUserInfo();
        return Response.ok(authResponse).build();
    }
}
