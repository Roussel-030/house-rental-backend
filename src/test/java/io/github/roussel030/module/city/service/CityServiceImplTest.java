package io.github.roussel030.module.city.service;

import io.github.roussel030.module.city.dto.CityRequest;
import io.github.roussel030.module.city.dto.CityResponse;
import io.github.roussel030.module.city.entity.City;
import io.github.roussel030.module.city.exception.CityAlreadyExistsException;
import io.github.roussel030.module.city.exception.CityNotFoundException;
import io.github.roussel030.module.city.repository.CityRepository;
import io.github.roussel030.module.country.entity.Country;
import io.github.roussel030.module.country.exception.CountryNotFoundException;
import io.github.roussel030.module.country.repository.CountryRepository;
import io.github.roussel030.module.neighborhood.repository.NeighborhoodRepository;
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
class CityServiceImplTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private NeighborhoodRepository neighborhoodRepository;

    @Mock
    private CountryRepository countryRepository;

    private CityServiceImpl cityService;

    @BeforeEach
    void setUp() {
        cityService = new CityServiceImpl(cityRepository, neighborhoodRepository, countryRepository);
    }

    @Test
    void createCity_Success() {
        // Given
        Long countryId = 1L;
        String cityName = "Paris";
        CityRequest request = new CityRequest(cityName, countryId);

        Country country = new Country();
        country.setId(countryId);
        country.setName("France");

        when(countryRepository.findByIdOptional(countryId)).thenReturn(Optional.of(country));
        when(cityRepository.existsByNameAndCountry(cityName, country)).thenReturn(false);

        // When
        CityResponse response = cityService.createCity(request);

        // Then
        assertNotNull(response);
        assertEquals(cityName, response.name());
        assertEquals(countryId, response.country().id());
        verify(cityRepository, times(1)).save(any(City.class));
    }

    @Test
    void createCity_CountryNotFound() {
        // Given
        Long countryId = 1L;
        CityRequest request = new CityRequest("Paris", countryId);

        when(countryRepository.findByIdOptional(countryId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CountryNotFoundException.class, () -> cityService.createCity(request));
        verify(cityRepository, never()).save(any(City.class));
    }

    @Test
    void createCity_AlreadyExists() {
        // Given
        Long countryId = 1L;
        String cityName = "Paris";
        CityRequest request = new CityRequest(cityName, countryId);

        Country country = new Country();
        country.setId(countryId);
        country.setName("France");

        when(countryRepository.findByIdOptional(countryId)).thenReturn(Optional.of(country));
        when(cityRepository.existsByNameAndCountry(cityName, country)).thenReturn(true);

        // When & Then
        assertThrows(CityAlreadyExistsException.class, () -> cityService.createCity(request));
        verify(cityRepository, never()).save(any(City.class));
    }

    @Test
    void getCities_Success() {
        // Given
        String search = "Par";
        int page = 0;
        int size = 10;

        Country country = new Country();
        country.setId(1L);
        country.setName("France");

        City city = new City();
        city.setId(1L);
        city.setName("Paris");
        city.setCountry(country);

        when(cityRepository.findAllPaginated(search, page, size)).thenReturn(List.of(city));
        when(cityRepository.countALl(search)).thenReturn(1L);

        // When
        PageResponse<CityResponse> response = cityService.getCities(search, page, size);

        // Then
        assertNotNull(response);
        assertEquals(1, response.items().size());
        assertEquals(1L, response.total());
        assertEquals(page, response.page());
        assertEquals(size, response.size());
        assertEquals("Paris", response.items().get(0).name());
    }

    @Test
    void updateCity_Success() {
        // Given
        Long cityId = 1L;
        Long countryId = 1L;
        String newName = "Lyon";
        CityRequest request = new CityRequest(newName, countryId);

        Country country = new Country();
        country.setId(countryId);
        country.setName("France");

        City city = new City();
        city.setId(cityId);
        city.setName("Paris");
        city.setCountry(country);

        when(cityRepository.findByIdOptional(cityId)).thenReturn(Optional.of(city));
        when(countryRepository.findByIdOptional(countryId)).thenReturn(Optional.of(country));
        when(cityRepository.existsByNameAndCountry(newName, country)).thenReturn(false);

        // When
        CityResponse response = cityService.updateCity(cityId, request);

        // Then
        assertNotNull(response);
        assertEquals(newName, response.name());
        verify(cityRepository, times(1)).update(city);
    }

    @Test
    void updateCity_CityNotFound() {
        // Given
        Long cityId = 1L;
        CityRequest request = new CityRequest("Lyon", 1L);

        when(cityRepository.findByIdOptional(cityId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CityNotFoundException.class, () -> cityService.updateCity(cityId, request));
        verify(cityRepository, never()).update(any(City.class));
    }

    @Test
    void updateCity_CountryNotFound() {
        // Given
        Long cityId = 1L;
        Long countryId = 1L;
        CityRequest request = new CityRequest("Lyon", countryId);

        City city = new City();
        city.setId(cityId);
        city.setName("Paris");

        when(cityRepository.findByIdOptional(cityId)).thenReturn(Optional.of(city));
        when(countryRepository.findByIdOptional(countryId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CountryNotFoundException.class, () -> cityService.updateCity(cityId, request));
        verify(cityRepository, never()).update(any(City.class));
    }

    @Test
    void updateCity_AlreadyExists() {
        // Given
        Long cityId = 1L;
        Long countryId = 1L;
        String newName = "Lyon";
        CityRequest request = new CityRequest(newName, countryId);

        Country country = new Country();
        country.setId(countryId);
        country.setName("France");

        City city = new City();
        city.setId(cityId);
        city.setName("Paris");
        city.setCountry(country);

        when(cityRepository.findByIdOptional(cityId)).thenReturn(Optional.of(city));
        when(countryRepository.findByIdOptional(countryId)).thenReturn(Optional.of(country));
        when(cityRepository.existsByNameAndCountry(newName, country)).thenReturn(true);

        // When & Then
        assertThrows(CityAlreadyExistsException.class, () -> cityService.updateCity(cityId, request));
        verify(cityRepository, never()).update(any(City.class));
    }

    @Test
    void deleteCity_Success() {
        // Given
        Long cityId = 1L;
        City city = new City();
        city.setId(cityId);

        when(cityRepository.findByIdOptional(cityId)).thenReturn(Optional.of(city));

        // When
        cityService.deleteCity(cityId);

        // Then
        verify(neighborhoodRepository, times(1)).deleteByCity(city);
        verify(cityRepository, times(1)).deleteCity(city);
    }

    @Test
    void deleteCity_NotFound() {
        // Given
        Long cityId = 1L;
        when(cityRepository.findByIdOptional(cityId)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(CityNotFoundException.class, () -> cityService.deleteCity(cityId));
        verify(neighborhoodRepository, never()).deleteByCity(any());
        verify(cityRepository, never()).deleteCity(any());
    }
}
