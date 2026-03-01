package io.github.roussel030.country.repository;

import io.github.roussel030.country.entity.Country;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
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
    public List<Country> findAllPaginated(int page, int size) {
        return findAll()
                .page(page, size)
                .list();
    }

    @Override
    public void update(Country country) {
        getEntityManager().merge(country);
    }

    @Override
    public boolean removeById(Long id) {
        return deleteById(id);
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
    public long countAll() {
        return count();
    }

}
