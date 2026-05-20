package io.github.roussel030.module.category.resource;

import io.github.roussel030.module.category.dto.CategoryRequest;
import io.github.roussel030.module.category.dto.CategoryResponse;
import io.github.roussel030.module.category.service.CategoryService;
import io.github.roussel030.shared.dto.PageResponse;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryResourceImplTest {

    @Mock
    private CategoryService categoryService;

    private CategoryResourceImpl categoryResource;

    @BeforeEach
    void setUp() {
        categoryResource = new CategoryResourceImpl(categoryService);
    }

    @Test
    void createCategory_ShouldReturnCreatedResponse() {
        // Arrange
        CategoryRequest request = new CategoryRequest("Apartment");
        CategoryResponse expectedResponse = CategoryResponse.builder().id(1L).name("Apartment").build();
        when(categoryService.createCategory(request)).thenReturn(expectedResponse);

        // Act
        Response response = categoryResource.createCategory(request);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(categoryService).createCategory(request);
    }

    @Test
    void getCategories_ShouldReturnOkResponseWithPageResponse() {
        // Arrange
        String search = "";
        int page = 0;
        int size = 10;
        PageResponse<CategoryResponse> expectedPageResponse = new PageResponse<>(
                List.of(CategoryResponse.builder().id(1L).name("Apartment").build()),
                0, 10, 1L
        );
        when(categoryService.getCategories(search, page, size)).thenReturn(expectedPageResponse);

        // Act
        Response response = categoryResource.getCategories(search, page, size);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedPageResponse, response.getEntity());
        verify(categoryService).getCategories(search, page, size);
    }

    @Test
    void updateCategory_ShouldReturnOkResponse() {
        // Arrange
        Long id = 1L;
        CategoryRequest request = new CategoryRequest("Studio");
        CategoryResponse expectedResponse = CategoryResponse.builder().id(id).name("Studio").build();
        when(categoryService.updateCategory(id, request)).thenReturn(expectedResponse);

        // Act
        Response response = categoryResource.updateCategory(id, request);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(categoryService).updateCategory(id, request);
    }

    @Test
    void deleteCategory_ShouldReturnNoContentResponse() {
        // Arrange
        Long id = 1L;

        // Act
        Response response = categoryResource.deleteCategory(id);

        // Assert
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(categoryService).deleteCategory(id);
    }
}
