package io.github.roussel030.module.option.repository;

import io.github.roussel030.module.option.entity.Option;

import java.util.List;
import java.util.Optional;

public interface OptionRepository {

    void save(Option option);
    List<Option> findAllPaginated(int page, int size);
    void update(Option option);
    boolean removeById(Long id);
    boolean existsByName(String name);
    Optional<Option> findByIdOptional(Long id);
    long countAll();

}
