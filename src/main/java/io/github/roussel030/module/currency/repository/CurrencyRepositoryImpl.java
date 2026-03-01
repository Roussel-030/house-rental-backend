package io.github.roussel030.module.currency.repository;

import io.github.roussel030.module.currency.entity.Currency;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CurrencyRepositoryImpl implements CurrencyRepository, PanacheRepository<Currency> {

    @Override
    public void save(Currency currency) {
        persist(currency);
    }

    @Override
    public List<Currency> findAllPaginated(int page, int size) {
        return findAll()
                .page(page, size)
                .list();
    }

    @Override
    public void update(Currency currency) {
        getEntityManager().merge(currency);
    }

    @Override
    public void deleteCurrency(Currency currency) {
        delete(currency);
    }

    @Override
    public boolean existsByCode(String code) {
        return find("code", code).firstResultOptional().isPresent();
    }

    @Override
    public Optional<Currency> findByIdOptional(Long id) {
        return find("id", id).firstResultOptional();
    }

    @Override
    public long countAll() {
        return count();
    }

}
