package io.github.roussel030.module.country.service;

import io.github.roussel030.module.country.dto.CountryRequest;
import io.github.roussel030.module.country.dto.CountryResponse;
import io.github.roussel030.module.country.entity.Country;
import io.github.roussel030.module.country.exception.CountryAlreadyExistsException;
import io.github.roussel030.module.country.exception.CountryNotFoundException;
import io.github.roussel030.module.country.repository.CountryRepository;
import io.github.roussel030.module.currency.entity.Currency;
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
class CountryServiceImplTest {

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private CurrencyRepository currencyRepository;

    private CountryServiceImpl countryService;

    @BeforeEach
    void setUp() {
        countryService = new CountryServiceImpl(countryRepository, currencyRepository);
    }

    @Test
    void createCountry_Success() {
        CountryRequest request = new CountryRequest("France", 1L);
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setCode("EUR");
        currency.setName("Euro");
        currency.setSymbol("€");

        when(countryRepository.existsByName(request.name())).thenReturn(false);
        when(currencyRepository.findByIdOptional(request.currencyId())).thenReturn(Optional.of(currency));

        CountryResponse response = countryService.createCountry(request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        assertEquals(currency.getCode(), response.currency().code());
        verify(countryRepository).save(any(Country.class));
    }

    @Test
    void createCountry_AlreadyExists_ThrowsException() {
        CountryRequest request = new CountryRequest("France", 1L);
        when(countryRepository.existsByName(request.name())).thenReturn(true);

        assertThrows(CountryAlreadyExistsException.class, () -> countryService.createCountry(request));
    }

    @Test
    void createCountry_CurrencyNotFound_ThrowsException() {
        CountryRequest request = new CountryRequest("France", 1L);
        when(countryRepository.existsByName(request.name())).thenReturn(false);
        when(currencyRepository.findByIdOptional(request.currencyId())).thenReturn(Optional.empty());

        assertThrows(CurrencyNotFoundException.class, () -> countryService.createCountry(request));
    }

    @Test
    void getCountries_Success() {
        String search = "";
        int page = 0;
        int size = 10;
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setCode("EUR");
        
        Country country = new Country();
        country.setId(1L);
        country.setName("France");
        country.setCurrency(currency);

        when(countryRepository.findAllPaginated(search, page, size)).thenReturn(List.of(country));
        when(countryRepository.countAll(search)).thenReturn(1L);

        PageResponse<CountryResponse> response = countryService.getCountries(search, page, size);

        assertNotNull(response);
        assertEquals(1, response.items().size());
        assertEquals(1, response.total());
    }

    @Test
    void updateCountry_Success() {
        Long id = 1L;
        CountryRequest request = new CountryRequest("Spain", 1L);
        Currency currency = new Currency();
        currency.setId(1L);
        currency.setCode("EUR");

        Country country = new Country();
        country.setId(id);
        country.setName("France");
        country.setCurrency(currency);

        when(countryRepository.findByIdOptional(id)).thenReturn(Optional.of(country));
        when(countryRepository.existsByName(request.name())).thenReturn(false);
        when(currencyRepository.findByIdOptional(request.currencyId())).thenReturn(Optional.of(currency));

        CountryResponse response = countryService.updateCountry(id, request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        verify(countryRepository).update(country);
    }

    @Test
    void deleteCountry_Success() {
        Long id = 1L;
        Country country = new Country();
        country.setId(id);

        when(countryRepository.findByIdOptional(id)).thenReturn(Optional.of(country));

        countryService.deleteCountry(id);

        verify(countryRepository).deleteCountry(country);
    }

    @Test
    void deleteCountry_NotFound_ThrowsException() {
        Long id = 1L;
        when(countryRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        assertThrows(CountryNotFoundException.class, () -> countryService.deleteCountry(id));
    }
}
