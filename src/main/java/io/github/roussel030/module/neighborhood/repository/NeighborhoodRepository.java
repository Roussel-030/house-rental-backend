package io.github.roussel030.module.neighborhood.repository;

import io.github.roussel030.module.city.entity.City;
import io.github.roussel030.module.country.entity.Country;
import io.github.roussel030.module.neighborhood.entity.Neighborhood;

import java.util.List;
import java.util.Optional;

public interface NeighborhoodRepository {

    void save(Neighborhood neighborhood);
    List<Neighborhood> findAllPaginated(String search, int page, int size);
    void update(Neighborhood neighborhood);
    void deleteByCity(City city);
    void deleteByCountry(Country country);
    void deleteNeighborhood(Neighborhood neighborhood);
    boolean existsByNameAndCity(String name, City  city);
    Optional<Neighborhood> findByIdOptional(Long id);
    long countAll(String search);

}
