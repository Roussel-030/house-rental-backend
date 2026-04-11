package io.github.roussel030.module.currency.resource;

import io.github.roussel030.module.currency.dto.CurrencyRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/currencies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CurrencyResource {

    @POST
    Response createCurrency(
            @Valid CurrencyRequest request
    );

    @GET
    Response getCurrencies(
            @QueryParam("search") String search,

            @QueryParam("page")
            @DefaultValue("0") int page,

            @QueryParam("size")
            @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    Response updateCurrency(
            @PathParam("id") Long id,
            @Valid CurrencyRequest request
    );

    @DELETE
    @Path("/{id}")
    Response deleteCurrency(
            @PathParam("id") Long id
    );

}