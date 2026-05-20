package io.github.roussel030.module.currency.resource;

import io.github.roussel030.module.currency.dto.CurrencyRequest;
import io.github.roussel030.module.currency.dto.CurrencyResponse;
import io.github.roussel030.module.currency.service.CurrencyService;
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
class CurrencyResourceImplTest {

    @Mock
    private CurrencyService currencyService;

    private CurrencyResourceImpl currencyResource;

    @BeforeEach
    void setUp() {
        currencyResource = new CurrencyResourceImpl(currencyService);
    }

    @Test
    void createCurrency_ShouldReturnCreatedResponse() {
        // Arrange
        CurrencyRequest request = new CurrencyRequest("MAD", "Moroccan Dirham", "DH");
        CurrencyResponse expectedResponse = CurrencyResponse.builder().id(1L).code("MAD").name("Moroccan Dirham").symbol("DH").build();
        when(currencyService.createCurrency(request)).thenReturn(expectedResponse);

        // Act
        Response response = currencyResource.createCurrency(request);

        // Assert
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(currencyService).createCurrency(request);
    }

    @Test
    void getCurrencies_ShouldReturnOkResponseWithPageResponse() {
        // Arrange
        String search = "";
        int page = 0;
        int size = 10;
        PageResponse<CurrencyResponse> expectedPageResponse = new PageResponse<>(
                List.of(CurrencyResponse.builder().id(1L).code("MAD").name("Moroccan Dirham").symbol("DH").build()),
                0, 10, 1L
        );
        when(currencyService.getCurrencies(search, page, size)).thenReturn(expectedPageResponse);

        // Act
        Response response = currencyResource.getCurrencies(search, page, size);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedPageResponse, response.getEntity());
        verify(currencyService).getCurrencies(search, page, size);
    }

    @Test
    void updateCurrency_ShouldReturnOkResponse() {
        // Arrange
        Long id = 1L;
        CurrencyRequest request = new CurrencyRequest("MAD", "Moroccan Dirham Updated", "DH");
        CurrencyResponse expectedResponse = CurrencyResponse.builder().id(id).code("MAD").name("Moroccan Dirham Updated").symbol("DH").build();
        when(currencyService.updateCurrency(id, request)).thenReturn(expectedResponse);

        // Act
        Response response = currencyResource.updateCurrency(id, request);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(expectedResponse, response.getEntity());
        verify(currencyService).updateCurrency(id, request);
    }

    @Test
    void deleteCurrency_ShouldReturnNoContentResponse() {
        // Arrange
        Long id = 1L;

        // Act
        Response response = currencyResource.deleteCurrency(id);

        // Assert
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        verify(currencyService).deleteCurrency(id);
    }
}
