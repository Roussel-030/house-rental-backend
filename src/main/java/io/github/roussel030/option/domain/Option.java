package io.github.roussel030.option.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "options")
public class Option extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String name;

    @Column(nullable = false)
    public String icon;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Option option = new Option();

        public Builder name(String name) {
            option.name = name;
            return this;
        }

        public Builder icon(String icon) {
            option.icon = icon;
            return this;
        }

        public Option build() {
            return option;
        }
    }

}
