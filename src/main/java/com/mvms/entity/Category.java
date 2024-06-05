package com.mvms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<FilmCategory> filmCategories;

    public Category() {
        this.filmCategories = new ArrayList<>();
        this.lastUpdate = LocalDateTime.now();
    }

    public Category(String name) {
        this.name = name;
        this.filmCategories = new ArrayList<>();
        this.lastUpdate = LocalDateTime.now();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<FilmCategory> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(List<FilmCategory> filmCategories) {
        this.filmCategories = filmCategories;
    }
}
