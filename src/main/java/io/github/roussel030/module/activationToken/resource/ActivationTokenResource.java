package io.github.roussel030.module.activationToken.resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/activation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ActivationTokenResource {

    @GET
    Response activate(
            @QueryParam("token") String rawToken
    );

}