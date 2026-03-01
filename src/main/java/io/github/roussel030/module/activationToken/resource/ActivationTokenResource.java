package io.github.roussel030.module.activationToken.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Path("/activation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Activation Token", description = "Operations related to activating user accounts")
public interface ActivationTokenResource {

    @GET
    @Operation(
            summary = "Activate user account",
            description = "Activates a user account using a provided activation token"
    )
    Response activate(
            @Parameter(
                    description = "The raw activation token sent to the user via email",
                    required = true,
                    example = "abc123-def456-ghi789"
            )
            @QueryParam("token") String rawToken
    );

}