package io.github.roussel030.module.user.resource;

import io.github.roussel030.module.user.dto.UserAdminRequest;
import io.github.roussel030.module.user.dto.UserRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "User", description = "Operations related to application users")
public interface UserResource {

    @POST
    @Operation(
            summary = "Create a user if user have status USER",
            description = "Creates a new user for the house rental application"
    )
    Response createUserAsUser(
            @Parameter(description = "User data to create")
            @Valid UserRequest request
    );

    @POST
    @Operation(
            summary = "Create a user if user have status ADMIN",
            description = "Creates a new user for the house rental application"
    )
    Response createUserAsAdmin(
            @Parameter(description = "User data to create")
            @Valid UserAdminRequest request
    );

    @GET
    @Operation(
            summary = "List users",
            description = "Returns a paginated list of users"
    )
    Response getUsers(
            @Parameter(description = "Search user by email or firstname or lastname")
            @QueryParam("search") String search,

            @Parameter(description = "Page number")
            @QueryParam("page")
            @DefaultValue("0") int page,

            @Parameter(description = "Page size")
            @QueryParam("size")
            @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a user if user have status ADMIN",
            description = "Updates an existing user identified by its ID"
    )
    Response updateUserAsAdmin(
            @Parameter(description = "ID of the user to update")
            @PathParam("id") Long id,

            @Parameter(description = "Updated user data")
            @Valid UserAdminRequest request
    );

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a user",
            description = "Deletes an existing user identified by its ID"
    )
    Response deleteUser(
            @Parameter(description = "ID of the user to delete")
            @PathParam("id") Long id
    );

}
