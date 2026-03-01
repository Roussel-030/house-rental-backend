package io.github.roussel030.module.category.repository;

import io.github.roussel030.module.category.entity.Category;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
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
    public List<Category> findAllPaginated(int page, int size) {
        return findAll()
                .page(page, size)
                .list();
    }

    @Override
    public void update(Category category) {
        getEntityManager().merge(category);
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
    public Optional<Category> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public long countAll() {
        return count();
    }

}
