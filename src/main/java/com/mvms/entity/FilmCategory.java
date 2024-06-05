package com.mvms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "film_category")
public class FilmCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    public FilmCategory() {
        this.lastUpdate = LocalDateTime.now();
    }

    public FilmCategory(Film film, Category category) {
        this.film = film;
        this.category = category;
        this.lastUpdate = LocalDateTime.now();
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
