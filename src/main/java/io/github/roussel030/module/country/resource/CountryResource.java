package io.github.roussel030.module.country.resource;

import io.github.roussel030.module.country.dto.CountryRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/countries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CountryResource {

    @POST
    Response createCountry(
            @Valid CountryRequest request
    );

    @GET
    Response getCountries(
            @QueryParam("search") String search,

            @QueryParam("page")
            @DefaultValue("0") int page,

            @QueryParam("size")
            @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    Response updateCountry(
            @PathParam("id") Long id,
            @Valid CountryRequest request
    );

    @DELETE
    @Path("/{id}")
    Response deleteCountry(
            @PathParam("id") Long id
    );

}