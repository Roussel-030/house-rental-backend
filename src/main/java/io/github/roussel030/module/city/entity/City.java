package io.github.roussel030.module.city.entity;

import io.github.roussel030.module.country.entity.Country;
import jakarta.persistence.*;

@Entity
@Table(
        name = "cities",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "country_id"})
)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

}
