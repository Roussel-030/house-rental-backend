package io.github.roussel030.module.category.repository;

import io.github.roussel030.module.category.entity.Category;

import java.util.Optional;
import java.util.List;

public interface CategoryRepository {

    void save(Category category);
    List<Category> findAllPaginated(String search, int page, int size);
    void update(Category category);
    void deleteCategory(Category category);
    boolean existsByName(String name);
    Optional<Category> findByIdOptional(Long id);
    long countAll(String search);

}

