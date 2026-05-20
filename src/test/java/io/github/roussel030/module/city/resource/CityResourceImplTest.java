package io.github.roussel030.module.city.resource;

import io.github.roussel030.module.city.dto.CityRequest;
import io.github.roussel030.module.city.dto.CityResponse;
import io.github.roussel030.module.city.service.CityService;
import io.github.roussel030.module.country.dto.CountryResponse;
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
class CityResourceImplTest {

    @Mock
    private CityService cityService;

    private CityResourceImpl cityResource;

    @BeforeEach
    void setUp() {
        cityResource = new CityResourceImpl(cityService);
    }

    @Test
    void createCity_ShouldReturnCreatedResponse() {
        // Arrange
        CityRequest request = new CityRequest("Casablanca", 1L);
        CityResponse expectedResponse = CityResponse.builder().id(1L).name("Casablanca").build();
        when(cityService.createCity(request)).thenReturn(expectedResponse);

        // Act
        Response response = cityResource.createCity(request);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(cityService).createCity(request);
    }

    @Test
    void getCities_ShouldReturnOkResponseWithPageResponse() {
        // Arrange
        String search = "";
        int page = 0;
        int size = 10;
        PageResponse<CityResponse> expectedPageResponse = new PageResponse<>(
                List.of(CityResponse.builder().id(1L).name("Casablanca").build()),
                0, 10, 1L
        );
        when(cityService.getCities(search, page, size)).thenReturn(expectedPageResponse);

        // Act
        Response response = cityResource.getCities(search, page, size);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedPageResponse, response.getEntity());
        verify(cityService).getCities(search, page, size);
    }

    @Test
    void updateCity_ShouldReturnOkResponse() {
        // Arrange
        Long id = 1L;
        CityRequest request = new CityRequest("Rabat", 1L);
        CityResponse expectedResponse = CityResponse.builder().id(id).name("Rabat").build();
        when(cityService.updateCity(id, request)).thenReturn(expectedResponse);

        // Act
        Response response = cityResource.updateCity(id, request);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(cityService).updateCity(id, request);
    }

    @Test
    void deleteCity_ShouldReturnNoContentResponse() {
        // Arrange
        Long id = 1L;

        // Act
        Response response = cityResource.deleteCity(id);

        // Assert
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(cityService).deleteCity(id);
    }
}
