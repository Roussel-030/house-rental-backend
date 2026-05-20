package io.github.roussel030.module.neighborhood.service;

import io.github.roussel030.module.city.entity.City;
import io.github.roussel030.module.city.exception.CityNotFoundException;
import io.github.roussel030.module.city.repository.CityRepository;
import io.github.roussel030.module.neighborhood.dto.NeighborhoodRequest;
import io.github.roussel030.module.neighborhood.dto.NeighborhoodResponse;
import io.github.roussel030.module.neighborhood.entity.Neighborhood;
import io.github.roussel030.module.neighborhood.exception.NeighborhoodAlreadyExistsException;
import io.github.roussel030.module.neighborhood.exception.NeighborhoodNotFoundException;
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
class NeighborhoodServiceImplTest {

    @Mock
    private NeighborhoodRepository neighborhoodRepository;

    @Mock
    private CityRepository cityRepository;

    private NeighborhoodServiceImpl neighborhoodService;

    @BeforeEach
    void setUp() {
        neighborhoodService = new NeighborhoodServiceImpl(neighborhoodRepository, cityRepository);
    }

    @Test
    void createNeighborhood_Success() {
        NeighborhoodRequest request = new NeighborhoodRequest("Downtown", 1L);
        City city = new City();
        city.setId(1L);
        city.setName("Paris");

        when(cityRepository.findByIdOptional(1L)).thenReturn(Optional.of(city));
        when(neighborhoodRepository.existsByNameAndCity(request.name(), city)).thenReturn(false);

        NeighborhoodResponse response = neighborhoodService.createNeighborhood(request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        assertEquals(city.getName(), response.city().name());
        verify(neighborhoodRepository).save(any(Neighborhood.class));
    }

    @Test
    void createNeighborhood_CityNotFound_ThrowsException() {
        NeighborhoodRequest request = new NeighborhoodRequest("Downtown", 1L);
        when(cityRepository.findByIdOptional(1L)).thenReturn(Optional.empty());

        assertThrows(CityNotFoundException.class, () -> neighborhoodService.createNeighborhood(request));
    }

    @Test
    void createNeighborhood_AlreadyExists_ThrowsException() {
        NeighborhoodRequest request = new NeighborhoodRequest("Downtown", 1L);
        City city = new City();
        city.setId(1L);
        city.setName("Paris");

        when(cityRepository.findByIdOptional(1L)).thenReturn(Optional.of(city));
        when(neighborhoodRepository.existsByNameAndCity(request.name(), city)).thenReturn(true);

        assertThrows(NeighborhoodAlreadyExistsException.class, () -> neighborhoodService.createNeighborhood(request));
    }

    @Test
    void getNeighborhoods_Success() {
        String search = "";
        int page = 0;
        int size = 10;
        City city = new City();
        city.setId(1L);
        city.setName("Paris");
        
        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setId(1L);
        neighborhood.setName("Downtown");
        neighborhood.setCity(city);

        when(neighborhoodRepository.findAllPaginated(search, page, size)).thenReturn(List.of(neighborhood));
        when(neighborhoodRepository.countAll(search)).thenReturn(1L);

        PageResponse<NeighborhoodResponse> response = neighborhoodService.getNeighborhoods(search, page, size);

        assertNotNull(response);
        assertEquals(1, response.items().size());
        assertEquals(1, response.total());
    }

    @Test
    void updateNeighborhood_Success() {
        Long id = 1L;
        NeighborhoodRequest request = new NeighborhoodRequest("Old Town", 1L);
        City city = new City();
        city.setId(1L);
        city.setName("Paris");

        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setId(id);
        neighborhood.setName("Downtown");
        neighborhood.setCity(city);

        when(neighborhoodRepository.findByIdOptional(id)).thenReturn(Optional.of(neighborhood));
        when(cityRepository.findByIdOptional(request.cityId())).thenReturn(Optional.of(city));
        when(neighborhoodRepository.existsByNameAndCity(request.name(), city)).thenReturn(false);

        NeighborhoodResponse response = neighborhoodService.updateNeighborhood(id, request);

        assertNotNull(response);
        assertEquals(request.name(), response.name());
        verify(neighborhoodRepository).update(neighborhood);
    }

    @Test
    void deleteNeighborhood_Success() {
        Long id = 1L;
        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setId(id);

        when(neighborhoodRepository.findByIdOptional(id)).thenReturn(Optional.of(neighborhood));

        neighborhoodService.deleteNeighborhood(id);

        verify(neighborhoodRepository).deleteNeighborhood(neighborhood);
    }

    @Test
    void deleteNeighborhood_NotFound_ThrowsException() {
        Long id = 1L;
        when(neighborhoodRepository.findByIdOptional(id)).thenReturn(Optional.empty());

        assertThrows(NeighborhoodNotFoundException.class, () -> neighborhoodService.deleteNeighborhood(id));
    }
}
