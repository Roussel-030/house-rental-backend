package io.github.roussel030.module.category.service;

import io.github.roussel030.module.category.dto.CategoryRequest;
import io.github.roussel030.module.category.dto.CategoryResponse;
import io.github.roussel030.module.category.entity.Category;
import io.github.roussel030.module.category.exception.CategoryAlreadyExistsException;
import io.github.roussel030.module.category.exception.CategoryNotFoundException;
import io.github.roussel030.module.category.repository.CategoryRepository;
import io.github.roussel030.shared.dto.PageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    void createCategory_Success() {
        CategoryRequest request = new CategoryRequest("Apartment");
        when(categoryRepository.existsByName(request.name())).thenReturn(false);

        CategoryResponse response = categoryService.createCategory(request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void createCategory_AlreadyExists_ThrowsException() {
        CategoryRequest request = new CategoryRequest("Apartment");
        when(categoryRepository.existsByName(request.name())).thenReturn(true);

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.createCategory(request));
    }

    @Test
    void getCategories_Success() {
        String search = "";
        int page = 0;
        int size = 10;
        Category category = new Category();
        category.setId(1L);
        category.setName("Apartment");

        when(categoryRepository.findAllPaginated(search, page, size)).thenReturn(List.of(category));
        when(categoryRepository.countAll(search)).thenReturn(1L);

        PageResponse<CategoryResponse> response = categoryService.getCategories(search, page, size);

        assertNotNull(response);
        assertEquals(1, response.items().size());
        assertEquals(1, response.total());
    }

    @Test
    void updateCategory_Success() {
        Long id = 1L;
        CategoryRequest request = new CategoryRequest("House");
        Category category = new Category();
        category.setId(id);
        category.setName("Apartment");

        when(categoryRepository.findByIdOptional(id)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByName(request.name())).thenReturn(false);

        CategoryResponse response = categoryService.updateCategory(id, request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        verify(categoryRepository).update(category);
    }

    @Test
    void updateCategory_NotFound_ThrowsException() {
        Long id = 1L;
        CategoryRequest request = new CategoryRequest("House");
        when(categoryRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(id, request));
    }

    @Test
    void updateCategory_AlreadyExists_ThrowsException() {
        Long id = 1L;
        CategoryRequest request = new CategoryRequest("House");
        Category category = new Category();
        category.setId(id);
        category.setName("Apartment");

        when(categoryRepository.findByIdOptional(id)).thenReturn(Optional.of(category));
        when(categoryRepository.existsByName(request.name())).thenReturn(true);

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryService.updateCategory(id, request));
    }

    @Test
    void deleteCategory_Success() {
        Long id = 1L;
        Category category = new Category();
        category.setId(id);

        when(categoryRepository.findByIdOptional(id)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(id);

        verify(categoryRepository).deleteCategory(category);
    }

    @Test
    void deleteCategory_NotFound_ThrowsException() {
        Long id = 1L;
        when(categoryRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(id));
    }
}
