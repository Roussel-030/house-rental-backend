package io.github.roussel030.category.domain;

import java.util.Optional;
import java.util.List;

public interface CategoryRepository {

    void save(Category category);
    List<Category> findAllPaginated(int page, int size);
    void update(Category category);
    boolean removeById(Long id);
    boolean existsByName(String name);
    Optional<Category> findByIdOptional(Long id);
    long countAll();

}

