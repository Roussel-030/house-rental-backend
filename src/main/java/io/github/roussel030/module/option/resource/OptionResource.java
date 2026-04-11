package io.github.roussel030.module.option.resource;

import io.github.roussel030.module.option.dto.OptionRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface OptionResource {

    @POST
    Response createOption(
            @Valid OptionRequest request
    );

    @GET
    Response getOptions(
            @QueryParam("search") String search,

            @QueryParam("page")
            @DefaultValue("0") int page,

            @QueryParam("size")
            @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    Response updateOption(
            @PathParam("id") Long id,
            @Valid OptionRequest request
    );

    @DELETE
    @Path("/{id}")
    Response deleteOption(
            @PathParam("id") Long id
    );

}