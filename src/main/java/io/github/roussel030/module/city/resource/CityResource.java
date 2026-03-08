package io.github.roussel030.module.city.resource;

import io.github.roussel030.module.city.dto.CityRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/cities")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(
        name = "Property City",
        description = "Operations related to cities where properties (houses) are located"
)
public interface CityResource {

    @POST
    @Operation(
            summary = "Create a city",
            description = "Creates a new city where a property (house) can be located"
    )
    Response createCity(
            @Parameter(
                    description = "City data to create (city where the property is located)"
            )
            @Valid CityRequest request
    );

    @GET
    @Operation(
            summary = "List cities",
            description = "Returns a paginated list of cities where properties (houses) are located"
    )
    Response getCities(
            @Parameter(description = "Search city by name")
            @QueryParam("search") String search,

            @Parameter(description = "Page number (starts from 0)")
            @QueryParam("page") @DefaultValue("0") int page,

            @Parameter(description = "Number of elements per page")
            @QueryParam("size") @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a city",
            description = "Updates an existing city where a property (house) is located"
    )
    Response updateCity(
            @Parameter(description = "ID of the city to update")
            @PathParam("id") Long id,

            @Parameter(description = "Updated city data")
            @Valid CityRequest request
    );

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a city",
            description = "Deletes an existing city where properties (houses) are located"
    )
    Response deleteCity(
            @Parameter(description = "ID of the city to delete")
            @PathParam("id") Long id
    );

}