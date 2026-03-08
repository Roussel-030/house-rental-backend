package io.github.roussel030.module.option.resource;

import io.github.roussel030.module.option.dto.OptionRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "House Option", description = "Operations related to house options (e.g., Garage, Swimming Pool, Balcony)")
public interface OptionResource {

    @POST
    @Operation(
            summary = "Create a house option",
            description = "Creates a new house option with the provided name and icon"
    )
    Response createOption(
            @Parameter(description = "House option data to create") @Valid OptionRequest request
    );

    @GET
    @Operation(
            summary = "List house options",
            description = "Returns a paginated list of house options"
    )
    Response getOptions(
            @Parameter(description = "Search option by name")
            @QueryParam("search") String search,

            @Parameter(description = "Page number")
            @QueryParam("page") @DefaultValue("0") int page,

            @Parameter(description = "Page size")
            @QueryParam("size") @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a house option",
            description = "Updates an existing house option identified by its ID"
    )
    Response updateOption(
            @Parameter(description = "ID of the house option to update") @PathParam("id") Long id,
            @Parameter(description = "Updated house option data") @Valid OptionRequest request
    );

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a house option",
            description = "Deletes an existing house option identified by its ID"
    )
    Response deleteOption(
            @Parameter(description = "ID of the house option to delete") @PathParam("id") Long id
    );

}