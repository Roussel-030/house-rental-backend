package io.github.roussel030.module.category.service;

import io.github.roussel030.module.category.dto.CategoryRequest;
import io.github.roussel030.module.category.dto.CategoryResponse;
import io.github.roussel030.shared.dto.PageResponse;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);
    PageResponse<CategoryResponse> getCategories(int page, int size);
    CategoryResponse updateCategory(Long id, CategoryRequest request);
    void  deleteCategory(Long id);

}
