package io.github.roussel030.category.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String name;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Category category = new Category();

        public Builder name(String name) {
            category.name = name;
            return this;
        }

        public Category build() {
            return category;
        }
    }
}
