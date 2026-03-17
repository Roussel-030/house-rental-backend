package io.github.roussel030.module.neighborhood.resource;

import io.github.roussel030.module.neighborhood.dto.NeighborhoodRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/neighborhoods")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(
        name = "Property Neighborhood",
        description = "Operations related to neighborhoods where properties (houses, apartments, etc.) are located"
)
public interface NeighborhoodResource {

    @POST
    @Operation(
            summary = "Create a neighborhood",
            description = "Creates a new neighborhood where a property (house, apartment, etc.) can be located"
    )
    Response createNeighborhood(
            @Parameter(
                    description = "Neighborhood data to create (neighborhood where the property is located)"
            )
            @Valid NeighborhoodRequest request
    );

    @GET
    @Operation(
            summary = "List neighborhoods",
            description = "Returns a paginated list of neighborhoods where properties are located"
    )
    Response getNeighborhoods(
            @Parameter(description = "Search neighborhood by name")
            @QueryParam("search") String search,

            @Parameter(description = "Page number (starts from 0)")
            @QueryParam("page") @DefaultValue("0") int page,

            @Parameter(description = "Number of elements per page")
            @QueryParam("size") @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a neighborhood",
            description = "Updates an existing neighborhood where a property is located"
    )
    Response updateNeighborhood(
            @Parameter(description = "ID of the neighborhood to update")
            @PathParam("id") Long id,

            @Parameter(description = "Updated neighborhood data")
            @Valid NeighborhoodRequest request
    );

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a neighborhood",
            description = "Deletes an existing neighborhood where properties are located"
    )
    Response deleteNeighborhood(
            @Parameter(description = "ID of the neighborhood to delete")
            @PathParam("id") Long id
    );

}