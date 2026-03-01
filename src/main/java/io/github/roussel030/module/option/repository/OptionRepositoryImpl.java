package io.github.roussel030.module.option.repository;

import io.github.roussel030.module.option.entity.Option;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OptionRepositoryImpl implements OptionRepository, PanacheRepository<Option> {

    @Override
    public void save(Option option) {
        persist(option);
    }

    @Override
    public List<Option> findAllPaginated(int page, int size) {
        return findAll()
                .page(page, size)
                .list();
    }

    @Override
    public void update(Option option) {
        getEntityManager().merge(option);
    }

    @Override
    public void deleteOption(Option option) {
        delete(option);
    }

    @Override
    public boolean existsByName(String name) {
        return find("name", name).firstResultOptional().isPresent();
    }

    @Override
    public Optional<Option> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public long countAll() {
        return count();
    }

}
