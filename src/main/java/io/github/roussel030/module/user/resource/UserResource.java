package io.github.roussel030.module.user.resource;

import io.github.roussel030.module.user.dto.UserAdminCreateRequest;
import io.github.roussel030.module.user.dto.UserAdminUpdateRequest;
import io.github.roussel030.module.user.dto.UserRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserResource {

    @POST
    Response createUserAsUser(
            @Valid UserRequest request
    );

    @POST
    Response createUserAsAdmin(
            @Valid UserAdminCreateRequest request
    );

    @GET
    Response getUsers(
            @QueryParam("search") String search,

            @QueryParam("page")
            @DefaultValue("0") int page,

            @QueryParam("size")
            @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    Response updateUserAsAdmin(
            @PathParam("id") Long id,
            @Valid UserAdminUpdateRequest request
    );

    @DELETE
    @Path("/{id}")
    Response deleteUser(
            @PathParam("id") Long id
    );

}
