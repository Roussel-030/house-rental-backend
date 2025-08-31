package io.github.roussel030.category.api;

import io.github.roussel030.category.api.dto.CategoryRequest;
import io.github.roussel030.category.api.dto.CategoryResponse;
import io.github.roussel030.category.domain.CategoryService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    private final CategoryService categoryService;

    public CategoryResource(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @POST
    public Response createCategory(@Valid CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @GET
    public Response getCategories(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size
    ) {
        PageResponse<CategoryResponse> response = categoryService.getCategories(page, size);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCategory(
            @PathParam("id") Long id,
            @Valid CategoryRequest request
    ) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        categoryService.deleteCategory(id);
        return Response.noContent().build();
    }

}
