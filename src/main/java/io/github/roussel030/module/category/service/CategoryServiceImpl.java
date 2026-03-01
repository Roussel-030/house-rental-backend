package io.github.roussel030.module.category.service;

import io.github.roussel030.module.category.dto.CategoryRequest;
import io.github.roussel030.module.category.dto.CategoryResponse;
import io.github.roussel030.module.category.entity.Category;
import io.github.roussel030.module.category.exception.CategoryAlreadyExistsException;
import io.github.roussel030.module.category.repository.CategoryRepository;
import io.github.roussel030.module.category.exception.CategoryNotFoundException;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if(categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException("Category already exists");
        }

        Category category = new Category();
        category.setName(category.getName());
        categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public PageResponse<CategoryResponse> getCategories(int page, int size) {
        List<CategoryResponse> categories = categoryRepository.findAllPaginated(page, size)
                .stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .toList();

        long total = getCountTotalCategory();

        return PageResponse.<CategoryResponse>builder()
                .items(categories)
                .page(page)
                .size(size)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findByIdOptional(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        if (!category.getName().equals(request.name()) &&
                categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException("Category with this name already exists");
        }

        category.setName(request.name());
        categoryRepository.update(category);

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findByIdOptional(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        categoryRepository.deleteCategory(category);
    }

    private Long getCountTotalCategory() {
        return categoryRepository.countAll();
    }

}
