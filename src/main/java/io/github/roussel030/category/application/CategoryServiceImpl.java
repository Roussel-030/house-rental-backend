package io.github.roussel030.category.application;

import io.github.roussel030.category.api.dto.CategoryRequest;
import io.github.roussel030.category.api.dto.CategoryResponse;
import io.github.roussel030.category.domain.Category;
import io.github.roussel030.category.domain.CategoryService;
import io.github.roussel030.category.exception.CategoryAlreadyExistsException;
import io.github.roussel030.category.domain.CategoryRepository;
import io.github.roussel030.category.exception.CategoryNotFoundException;
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

        Category category = Category.builder()
                .name(request.name())
                .build();
        categoryRepository.save(category);

        return CategoryResponse.builder()
                .id(category.id)
                .name(category.name)
                .build();
    }

    @Override
    public PageResponse<CategoryResponse> getCategories(int page, int size) {
        List<CategoryResponse> categories = categoryRepository.findAllPaginated(page, size)
                .stream()
                .map(category -> CategoryResponse.builder()
                        .id(category.id)
                        .name(category.name)
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

        if (!category.name.equals(request.name()) &&
                categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException("Category with this name already exists");
        }

        category.name = request.name();
        categoryRepository.update(category);

        return CategoryResponse.builder()
                .id(category.id)
                .name(category.name)
                .build();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        if (!categoryRepository.removeById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
    }

    private Long getCountTotalCategory() {
        return categoryRepository.countAll();
    }

}
