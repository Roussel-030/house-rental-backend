package io.github.roussel030.module.country.repository;

import io.github.roussel030.module.country.entity.Country;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CountryRepositoryImpl implements CountryRepository, PanacheRepository<Country> {

    @Override
    public void save(Country country) {
        persist(country);
    }

    @Override
    public List<Country> findAllPaginated(String search, int page, int size) {
        Sort sort = Sort.ascending("name");
        String baseQuery = "SELECT c FROM Country c LEFT JOIN FETCH c.currency";

        var query = (search == null || search.isBlank())
                ? find(baseQuery, sort)
                : find(baseQuery + " WHERE LOWER(c.name) LIKE ?1", sort, "%" + search.toLowerCase() + "%");

        return query.page(page, size).list();
    }

    @Override
    public void update(Country country) {
        getEntityManager().merge(country);
    }

    @Override
    public void deleteCountry(Country country) {
        delete(country);
    }

    @Override
    public boolean existsByName(String name) {
        return find("name", name).firstResultOptional().isPresent();
    }

    @Override
    public Optional<Country> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public long countAll(String search) {
        if (search == null || search.isBlank()) {
            return count();
        }

        return count("LOWER(name) LIKE ?1", "%" + search.toLowerCase() + "%");
    }

}
