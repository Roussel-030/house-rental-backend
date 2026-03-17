package io.github.roussel030.module.neighborhood.service;

import io.github.roussel030.module.city.dto.CityResponse;
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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class NeighborhoodServiceImpl implements NeighborhoodService {

    private final NeighborhoodRepository neighborhoodRepository;
    private final CityRepository cityRepository;

    public NeighborhoodServiceImpl(
            NeighborhoodRepository neighborhoodRepository,
            CityRepository cityRepository
    ) {
        this.neighborhoodRepository = neighborhoodRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    @Transactional
    public NeighborhoodResponse createNeighborhood(NeighborhoodRequest request) {
        City city = cityRepository.findByIdOptional(request.cityId())
                .orElseThrow(() -> new CityNotFoundException("City not found with id: " + request.cityId()));

        if(neighborhoodRepository.existsByNameAndCity(request.name(), city)) {
            throw new NeighborhoodAlreadyExistsException(
                    "Neighborhood '" + request.name() + "' already exists in city '" + city.getName() + "'"
            );
        }

        Neighborhood neighborhood = new Neighborhood();
        neighborhood.setName(request.name());
        neighborhood.setCity(city);
        neighborhoodRepository.save(neighborhood);

        return NeighborhoodResponse.builder()
                .id(neighborhood.getId())
                .name(request.name())
                .city(
                        CityResponse.builder()
                                .id(city.getId())
                                .name(city.getName())
                                .build()
                )
                .build();
    }

    @Override
    public PageResponse<NeighborhoodResponse> getNeighborhoods(String search, int page, int size) {
        List<NeighborhoodResponse> neighborhoods = neighborhoodRepository.findAllPaginated(search, page, size)
                .stream()
                .map(neighborhood -> NeighborhoodResponse.builder()
                        .id(neighborhood.getId())
                        .name(neighborhood.getName())
                        .city(
                                CityResponse.builder()
                                        .id(neighborhood.getCity().getId())
                                        .name(neighborhood.getCity().getName())
                                        .build()
                        )
                        .build())
                .toList();

        long total = getCountTotalNeighborhood(search);

        return PageResponse.<NeighborhoodResponse>builder()
                .items(neighborhoods)
                .page(page)
                .size(size)
                .total(total)
                .build();
    }

    @Override
    @Transactional
    public NeighborhoodResponse updateNeighborhood(Long id, NeighborhoodRequest request) {
        Neighborhood neighborhood = neighborhoodRepository.findByIdOptional(id)
                .orElseThrow(() -> new NeighborhoodNotFoundException("Neighborhood not found with id: " + id));

        City city = cityRepository.findByIdOptional(request.cityId())
                .orElseThrow(() -> new CityNotFoundException("City not found with id: " + request.cityId()));

        if(!neighborhood.getName().equals(request.name()) &&
                neighborhoodRepository.existsByNameAndCity(request.name(), city)) {
            throw new NeighborhoodAlreadyExistsException(
                    "Neighborhood '" + request.name() + "' already exists in city '" + city.getName() + "'"
            );
        }

        neighborhood.setName(request.name());
        neighborhood.setCity(city);
        neighborhoodRepository.update(neighborhood);

        return NeighborhoodResponse.builder()
                .id(neighborhood.getId())
                .name(request.name())
                .city(
                        CityResponse.builder()
                                .id(city.getId())
                                .name(city.getName())
                                .build()
                )
                .build();
    }

    @Override
    @Transactional
    public void deleteNeighborhood(Long id) {
        Neighborhood neighborhood = neighborhoodRepository.findByIdOptional(id)
                .orElseThrow(() -> new NeighborhoodNotFoundException("Neighborhood not found with id: " + id));

        neighborhoodRepository.deleteNeighborhood(neighborhood);
    }

    private Long getCountTotalNeighborhood(String search) {
        return neighborhoodRepository.countAll(search);
    }

}
