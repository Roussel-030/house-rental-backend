package io.github.roussel030.module.category.repository;

import io.github.roussel030.module.category.entity.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoryRepositoryImpl implements CategoryRepository, PanacheRepository<Category> {

    @Override
    public void save(Category category) {
        persist(category);
    }

    @Override
    public List<Category> findAllPaginated(String search, int page, int size) {
        Sort sort = Sort.descending("id");

        var query = (search == null || search.isBlank())
                ? findAll(sort)
                : find("LOWER(name) like ?1", sort, "%" + search.toLowerCase() + "%");

        return query.page(page, size).list();
    }

    @Override
    public void update(Category category) {
        getEntityManager().merge(category);
    }

    @Override
    public void deleteCategory(Category category) {
        delete(category);
    }

    @Override
    public boolean existsByName(String name) {
        return find("name", name).firstResultOptional().isPresent();
    }

    @Override
    public Optional<Category> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public long countAll(String search) {
        if (search == null || search.isBlank()) {
            return count();
        }

        return count("LOWER(name) like ?1", "%" + search.toLowerCase() + "%");
    }

}
