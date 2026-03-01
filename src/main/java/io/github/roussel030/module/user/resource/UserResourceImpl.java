package io.github.roussel030.module.user.resource;

import io.github.roussel030.shared.dto.PageResponse;
import io.github.roussel030.module.user.dto.UserAdminRequest;
import io.github.roussel030.module.user.dto.UserRequest;
import io.github.roussel030.module.user.dto.UserResponse;
import io.github.roussel030.module.user.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserResourceImpl implements UserResource {

    private final UserService userService;

    public UserResourceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Response createUserAsUser(@Valid UserRequest request) {
        UserResponse response = userService.createUserAsUser(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response createUserAsAdmin(@Valid UserAdminRequest request) {
        UserResponse response = userService.createUserAsAdmin(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response getUsers(int page, int size) {
        PageResponse<UserResponse> responses = userService.getUsers(page, size);
        return Response.ok(responses).build();
    }

    @Override
    public Response updateUserAsAdmin(Long id, @Valid UserAdminRequest request) {
        UserResponse response = userService.updateUserAsAdmin(id, request);
        return Response.ok(response).build();
    }

    @Override
    public Response deleteUser(Long id) {
        userService.deleteUser(id);
        return Response.noContent().build();
    }

}
