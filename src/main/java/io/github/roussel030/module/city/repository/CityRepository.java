package io.github.roussel030.module.city.repository;

import io.github.roussel030.module.city.entity.City;
import io.github.roussel030.module.country.entity.Country;

import java.util.List;
import java.util.Optional;

public interface CityRepository {

    void save(City city);
    List<City> findAllPaginated(int page, int size);
    void update(City city);
    void deleteByCountry(Country country);
    void deleteCity(City city);
    boolean existsByNameAndCountry(String name, Country country);
    Optional<City> findByIdOptional(Long id);
    long countALl();

}
