package io.github.roussel030.module.category.resource;

import io.github.roussel030.module.category.dto.CategoryRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CategoryResource {

    @POST
    Response createCategory(
            @Valid CategoryRequest request
    );

    @GET
    Response getCategories(
            @QueryParam("search") String search,

            @QueryParam("page")
            @DefaultValue("0") int page,

            @QueryParam("size")
            @DefaultValue("10") int size
    );

    @PUT
    @Path("/{id}")
    Response updateCategory(
            @PathParam("id") Long id,
            @Valid CategoryRequest request
    );

    @DELETE
    @Path("/{id}")
    Response deleteCategory(
            @PathParam("id") Long id
    );

}