package io.github.roussel030.category.resource;

import io.github.roussel030.category.dto.CategoryRequest;
import io.github.roussel030.category.dto.CategoryResponse;
import io.github.roussel030.category.service.CategoryService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class CategoryResourceImpl implements CategoryResource {

    private final CategoryService categoryService;

    public CategoryResourceImpl(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public Response createCategory(@Valid CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return Response.status(Response.Status.CREATED).entity(response).build();
    }

    @Override
    public Response getCategories(int page, int size) {
        PageResponse<CategoryResponse> responses = categoryService.getCategories(page, size);
        return Response.ok(responses).build();
    }

    @Override
    public Response updateCategory(Long id, @Valid CategoryRequest request) {
        CategoryResponse response = categoryService.updateCategory(id, request);
        return Response.ok(response).build();
    }

    @Override
    public Response deleteCategory(Long id) {
        categoryService.deleteCategory(id);
        return Response.noContent().build();
    }

}
