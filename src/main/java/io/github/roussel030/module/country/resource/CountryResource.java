package io.github.roussel030.module.country.resource;

import io.github.roussel030.module.country.dto.CountryRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Path("/countries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(
        name = "Property Country",
        description = "Operations related to countries where properties (houses) are located"
)
public interface CountryResource {

    @POST
    @Operation(
            summary = "Create a country",
            description = "Creates a new country where a property (house) can be located"
    )
    Response createCountry(
            @Parameter(
                    description = "Country data to create (country where the property is located)"
            )
            @Valid CountryRequest request
    );

    @GET
    @Operation(
            summary = "List countries",
            description = "Returns a paginated list of countries where properties (houses) are located"
    )
    Response getCountries(
            @Parameter(description = "Search country by name")
            @QueryParam("search") String search,

            @Parameter(description = "Page number (starts from 0)")
            @QueryParam("page") @DefaultValue("0") int page,

            @Parameter(description = "Number of elements per page")
            @QueryParam("size") @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a country",
            description = "Updates an existing country where a property (house) is located"
    )
    Response updateCountry(
            @Parameter(description = "ID of the country to update")
            @PathParam("id") Long id,

            @Parameter(description = "Updated country data")
            @Valid CountryRequest request
    );

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a country",
            description = "Deletes an existing country where properties (houses) are located"
    )
    Response deleteCountry(
            @Parameter(description = "ID of the country to delete")
            @PathParam("id") Long id
    );

}