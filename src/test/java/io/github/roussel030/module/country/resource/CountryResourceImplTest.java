package io.github.roussel030.module.country.resource;

import io.github.roussel030.module.country.dto.CountryRequest;
import io.github.roussel030.module.country.dto.CountryResponse;
import io.github.roussel030.module.country.service.CountryService;
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
class CountryResourceImplTest {

    @Mock
    private CountryService countryService;

    private CountryResourceImpl countryResource;

    @BeforeEach
    void setUp() {
        countryResource = new CountryResourceImpl(countryService);
    }

    @Test
    void createCountry_ShouldReturnCreatedResponse() {
        // Arrange
        CountryRequest request = new CountryRequest("Morocco", 1L);
        CountryResponse expectedResponse = CountryResponse.builder().id(1L).name("Morocco").build();
        when(countryService.createCountry(request)).thenReturn(expectedResponse);

        // Act
        Response response = countryResource.createCountry(request);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(countryService).createCountry(request);
    }

    @Test
    void getCountries_ShouldReturnOkResponseWithPageResponse() {
        // Arrange
        String search = "";
        int page = 0;
        int size = 10;
        PageResponse<CountryResponse> expectedPageResponse = new PageResponse<>(
                List.of(CountryResponse.builder().id(1L).name("Morocco").build()),
                0, 10, 1L
        );
        when(countryService.getCountries(search, page, size)).thenReturn(expectedPageResponse);

        // Act
        Response response = countryResource.getCountries(search, page, size);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedPageResponse, response.getEntity());
        verify(countryService).getCountries(search, page, size);
    }

    @Test
    void updateCountry_ShouldReturnOkResponse() {
        // Arrange
        Long id = 1L;
        CountryRequest request = new CountryRequest("Morocco Updated", 1L);
        CountryResponse expectedResponse = CountryResponse.builder().id(id).name("Morocco Updated").build();
        when(countryService.updateCountry(id, request)).thenReturn(expectedResponse);

        // Act
        Response response = countryResource.updateCountry(id, request);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(countryService).updateCountry(id, request);
    }

    @Test
    void deleteCountry_ShouldReturnNoContentResponse() {
        // Arrange
        Long id = 1L;

        // Act
        Response response = countryResource.deleteCountry(id);

        // Assert
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(countryService).deleteCountry(id);
    }
}
