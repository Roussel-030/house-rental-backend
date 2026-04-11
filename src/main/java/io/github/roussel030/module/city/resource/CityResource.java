package io.github.roussel030.module.city.resource;

import io.github.roussel030.module.city.dto.CityRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CityResource {

    @POST
    Response createCity(
            @Valid CityRequest request
    );

    @GET
    Response getCities(
            @QueryParam("search") String search,

            @QueryParam("page")
            @DefaultValue("0") int page,

            @QueryParam("size")
            @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    Response updateCity(
            @PathParam("id") Long id,
            @Valid CityRequest request
    );

    @DELETE
    @Path("/{id}")
    Response deleteCity(
            @PathParam("id") Long id
    );

}