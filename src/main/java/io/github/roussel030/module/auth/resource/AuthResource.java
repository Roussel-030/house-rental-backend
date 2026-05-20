package io.github.roussel030.module.auth.resource;

import io.github.roussel030.module.auth.dto.AuthRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AuthResource {

    @POST
    @Path("/login")
    Response login(@Valid AuthRequest request);

    @GET
    @Path("/me")
    Response me();

}
