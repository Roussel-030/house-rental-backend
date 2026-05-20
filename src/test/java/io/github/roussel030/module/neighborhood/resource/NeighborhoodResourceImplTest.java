package io.github.roussel030.module.neighborhood.resource;

import io.github.roussel030.module.neighborhood.dto.NeighborhoodRequest;
import io.github.roussel030.module.neighborhood.dto.NeighborhoodResponse;
import io.github.roussel030.module.neighborhood.service.NeighborhoodService;
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
class NeighborhoodResourceImplTest {

    @Mock
    private NeighborhoodService neighborhoodService;

    private NeighborhoodResourceImpl neighborhoodResource;

    @BeforeEach
    void setUp() {
        neighborhoodResource = new NeighborhoodResourceImpl(neighborhoodService);
    }

    @Test
    void createNeighborhood_ShouldReturnCreatedResponse() {
        // Arrange
        NeighborhoodRequest request = new NeighborhoodRequest("Maarif", 1L);
        NeighborhoodResponse expectedResponse = NeighborhoodResponse.builder().id(1L).name("Maarif").build();
        when(neighborhoodService.createNeighborhood(request)).thenReturn(expectedResponse);

        // Act
        Response response = neighborhoodResource.createNeighborhood(request);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(neighborhoodService).createNeighborhood(request);
    }

    @Test
    void getNeighborhoods_ShouldReturnOkResponseWithPageResponse() {
        // Arrange
        String search = "";
        int page = 0;
        int size = 10;
        PageResponse<NeighborhoodResponse> expectedPageResponse = new PageResponse<>(
                List.of(NeighborhoodResponse.builder().id(1L).name("Maarif").build()),
                0, 10, 1L
        );
        when(neighborhoodService.getNeighborhoods(search, page, size)).thenReturn(expectedPageResponse);

        // Act
        Response response = neighborhoodResource.getNeighborhoods(search, page, size);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedPageResponse, response.getEntity());
        verify(neighborhoodService).getNeighborhoods(search, page, size);
    }

    @Test
    void updateNeighborhood_ShouldReturnOkResponse() {
        // Arrange
        Long id = 1L;
        NeighborhoodRequest request = new NeighborhoodRequest("Maarif Updated", 1L);
        NeighborhoodResponse expectedResponse = NeighborhoodResponse.builder().id(id).name("Maarif Updated").build();
        when(neighborhoodService.updateNeighborhood(id, request)).thenReturn(expectedResponse);

        // Act
        Response response = neighborhoodResource.updateNeighborhood(id, request);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(neighborhoodService).updateNeighborhood(id, request);
    }

    @Test
    void deleteNeighborhood_ShouldReturnNoContentResponse() {
        // Arrange
        Long id = 1L;

        // Act
        Response response = neighborhoodResource.deleteNeighborhood(id);

        // Assert
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(neighborhoodService).deleteNeighborhood(id);
    }
}
