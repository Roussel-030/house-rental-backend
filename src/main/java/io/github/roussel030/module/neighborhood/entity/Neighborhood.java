package io.github.roussel030.module.neighborhood.entity;

import io.github.roussel030.module.city.entity.City;
import jakarta.persistence.*;

@Entity
@Table(
        name = "neighborhoods",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "city_id"})
)
public class Neighborhood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public City getCity() {
        return city;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
