package io.github.roussel030.module.city.repository;

import io.github.roussel030.module.city.entity.City;
import io.github.roussel030.module.country.entity.Country;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CityRepositoryImpl implements CityRepository, PanacheRepository<City> {

    @Override
    public void save(City city) {
        persist(city);
    }

    @Override
    public List<City> findAllPaginated(int page, int size) {
        return find(""" 
                          SELECT c FROM City c
                          LEFT JOIN FETCH c.country
                          """)
                .page(page, size)
                .list();
    }

    @Override
    public void update(City city) {
        getEntityManager().merge(city);
    }

    @Override
    public void deleteByCountry(Country country) {
        delete("country", country);
    }

    @Override
    public void deleteCity(City city) {
        delete(city);
    }

    @Override
    public boolean existsByNameAndCountry(String name, Country country) {
        return find("name = ?1 and country = ?2", name, country)
                .firstResultOptional()
                .isPresent();
    }

    @Override
    public Optional<City> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public long countALl() {
        return count();
    }

}
