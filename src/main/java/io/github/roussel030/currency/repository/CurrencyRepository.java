package io.github.roussel030.currency.repository;

import io.github.roussel030.currency.entity.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {

    void save(Currency currency);
    List<Currency> findAllPaginated(int page, int size);
    void update(Currency currency);
    boolean removeById(Long id);
    boolean existsByCode(String code);
    Optional<Currency> findByIdOptional(Long id);
    long countAll();

}
