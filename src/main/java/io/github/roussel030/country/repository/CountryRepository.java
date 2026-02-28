package io.github.roussel030.country.repository;

import io.github.roussel030.country.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CountryRepository {

    void save(Country country);
    List<Country> findAllPaginated(int page, int size);
    void update(Country country);
    boolean removeById(Long id);
    boolean existsByName(String name);
    Optional<Country> findByIdOptional(Long id);
    long countAll();

}
