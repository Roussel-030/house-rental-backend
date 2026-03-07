package io.github.roussel030.module.category.resource;

import io.github.roussel030.module.category.dto.CategoryRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "House Category", description = "Operations related to house categories")
public interface CategoryResource {

    @POST
    @Operation(
            summary = "Create a house category",
            description = "Creates a new house category with the provided information"
    )
    Response createCategory(
            @Parameter(description = "House category data to create")
            @Valid CategoryRequest request
    );

    @GET
    @Operation(
            summary = "List house categories",
            description = "Returns a paginated list of house categories with optional search by name"
    )
    Response getCategories(
            @Parameter(description = "Search category by name")
            @QueryParam("search") String search,

            @Parameter(description = "Page number")
            @QueryParam("page") @DefaultValue("0") int page,

            @Parameter(description = "Page size")
            @QueryParam("size") @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    @Operation(
            summary = "Update a house category",
            description = "Updates an existing house category identified by its ID"
    )
    Response updateCategory(
            @Parameter(description = "ID of the house category to update") @PathParam("id") Long id,
            @Parameter(description = "Updated house category data") @Valid CategoryRequest request
    );

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a house category",
            description = "Deletes an existing house category identified by its ID"
    )
    Response deleteCategory(
            @Parameter(description = "ID of the house category to delete") @PathParam("id") Long id
    );

}