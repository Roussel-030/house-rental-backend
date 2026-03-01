package io.github.roussel030.module.currency.resource;

import io.github.roussel030.module.currency.dto.CurrencyRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/currencies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Currency", description = "Operations related to currencies of a property's country")
public interface CurrencyResource {

    @POST
    @Operation(
            summary = "Create a currency",
            description = "Creates a new currency for a property's country"
    )
    Response createCurrency(
            @Parameter(description = "Currency data to create") @Valid CurrencyRequest request
    );

    @GET
    @Operation(
            summary = "List currencies",
            description = "Returns a paginated list of currencies"
    )
    Response getCurrencies(
            @Parameter(description = "Page number") @QueryParam("page") @DefaultValue("0") int page,
            @Parameter(description = "Page size") @QueryParam("size") @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a currency",
            description = "Updates an existing currency identified by its ID"
    )
    Response updateCurrency(
            @Parameter(description = "ID of the currency to update") @PathParam("id") Long id,
            @Parameter(description = "Updated currency data") @Valid CurrencyRequest request
    );

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a currency",
            description = "Deletes an existing currency identified by its ID"
    )
    Response deleteCurrency(
            @Parameter(description = "ID of the currency to delete") @PathParam("id") Long id
    );

}