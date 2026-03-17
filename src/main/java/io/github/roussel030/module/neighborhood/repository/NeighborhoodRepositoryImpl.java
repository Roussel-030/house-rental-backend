package io.github.roussel030.module.neighborhood.repository;

import io.github.roussel030.module.city.entity.City;
import io.github.roussel030.module.neighborhood.entity.Neighborhood;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class NeighborhoodRepositoryImpl implements NeighborhoodRepository, PanacheRepository<Neighborhood> {

    @Override
    public void save(Neighborhood neighborhood) {
        persist(neighborhood);
    }

    @Override
    public List<Neighborhood> findAllPaginated(String search, int page, int size) {
        Sort sort = Sort.ascending("name");
        String baseQuery = "SELECT n FROM Neighborhood n LEFT JOIN FETCH n.city";

        var query = (search == null || search.isBlank())
                ? find(baseQuery, sort)
                : find(baseQuery + " WHERE LOWER(n.name) LIKE ?1", sort, "%" + search.toLowerCase() + "%");

        return query.page(page, size).list();
    }

    @Override
    public void update(Neighborhood neighborhood) {
        getEntityManager().merge(neighborhood);
    }

    @Override
    public void deleteByCity(City city) {
        delete("city", city);
    }

    @Override
    public void deleteNeighborhood(Neighborhood neighborhood) {
        delete(neighborhood);
    }

    @Override
    public boolean existsByNameAndCity(String name, City city) {
        return false;
    }

    @Override
    public Optional<Neighborhood> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public long countAll(String search) {
        if(search == null || search.isBlank()) {
            return count();
        }

        return count("LOWER(name) LIKE ?1", "%" + search.toLowerCase() + "%");
    }

}
