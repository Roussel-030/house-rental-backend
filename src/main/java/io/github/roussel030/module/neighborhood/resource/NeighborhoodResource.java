package io.github.roussel030.module.neighborhood.resource;

import io.github.roussel030.module.neighborhood.dto.NeighborhoodRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/neighborhoods")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface NeighborhoodResource {

    @POST
    Response createNeighborhood(
            @Valid NeighborhoodRequest request
    );

    @GET
    Response getNeighborhoods(
            @QueryParam("search") String search,

            @QueryParam("page")
            @DefaultValue("0") int page,

            @QueryParam("size")
            @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    Response updateNeighborhood(
            @PathParam("id") Long id,
            @Valid NeighborhoodRequest request
    );

    @DELETE
    @Path("/{id}")
    Response deleteNeighborhood(
            @PathParam("id") Long id
    );

}