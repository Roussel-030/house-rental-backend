package io.github.roussel030.currency.infra;

import io.github.roussel030.currency.domain.Currency;
import io.github.roussel030.currency.domain.CurrencyRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import java.util.List;
import java.util.Optional;

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
    public boolean removeById(Long id) {
        return deleteById(id);
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
