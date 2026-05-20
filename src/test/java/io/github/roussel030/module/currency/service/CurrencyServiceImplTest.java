package io.github.roussel030.module.currency.service;

import io.github.roussel030.module.currency.dto.CurrencyRequest;
import io.github.roussel030.module.currency.dto.CurrencyResponse;
import io.github.roussel030.module.currency.entity.Currency;
import io.github.roussel030.module.currency.exception.CurrencyAlreadyExistsException;
import io.github.roussel030.module.currency.exception.CurrencyNotFoundException;
import io.github.roussel030.module.currency.repository.CurrencyRepository;
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
class CurrencyServiceImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        currencyService = new CurrencyServiceImpl(currencyRepository);
    }

    @Test
    void createCurrency_Success() {
        CurrencyRequest request = new CurrencyRequest("EUR", "Euro", "€");
        when(currencyRepository.existsByCode(request.code())).thenReturn(false);

        CurrencyResponse response = currencyService.createCurrency(request);

        assertNotNull(response);
        assertEquals(request.code(), response.code());
        assertEquals(request.name(), response.name());
        assertEquals(request.symbol(), response.symbol());
        verify(currencyRepository).save(any(Currency.class));
    }

    @Test
    void createCurrency_AlreadyExists_ThrowsException() {
        CurrencyRequest request = new CurrencyRequest("EUR", "Euro", "€");
        when(currencyRepository.existsByCode(request.code())).thenReturn(true);

        assertThrows(CurrencyAlreadyExistsException.class, () -> currencyService.createCurrency(request));
    }

    @Test
    void getCurrencies_Success() {
        String search = "";
        int page = 0;
        int size = 10;
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setCode("EUR");

        when(currencyRepository.findAllPaginated(search, page, size)).thenReturn(List.of(currency));
        when(currencyRepository.countAll(search)).thenReturn(1L);

        PageResponse<CurrencyResponse> response = currencyService.getCurrencies(search, page, size);

        assertNotNull(response);
        assertEquals(1, response.items().size());
        assertEquals(1, response.total());
    }

    @Test
    void updateCurrency_Success() {
        Long id = 1L;
        CurrencyRequest request = new CurrencyRequest("USD", "US Dollar", "$");
        Currency currency = new Currency();
        currency.setId(id);
        currency.setCode("EUR");

        when(currencyRepository.findByIdOptional(id)).thenReturn(Optional.of(currency));
        when(currencyRepository.existsByCode(request.code())).thenReturn(false);

        CurrencyResponse response = currencyService.updateCurrency(id, request);

        assertNotNull(response);
        assertEquals(request.code(), response.code());
        verify(currencyRepository).update(currency);
    }

    @Test
    void deleteCurrency_Success() {
        Long id = 1L;
        Currency currency = new Currency();
        currency.setId(id);

        when(currencyRepository.findByIdOptional(id)).thenReturn(Optional.of(currency));

        currencyService.deleteCurrency(id);

        verify(currencyRepository).deleteCurrency(currency);
    }

    @Test
    void deleteCurrency_NotFound_ThrowsException() {
        Long id = 1L;
        when(currencyRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        assertThrows(CurrencyNotFoundException.class, () -> currencyService.deleteCurrency(id));
    }
}
