package com.mvms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "language")
public class Language {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Long languageId;

    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "language", cascade = CascadeType.ALL)
    private List<Film> films;

    public Language() {
        this.films = new ArrayList<>();
        this.lastUpdate = LocalDateTime.now();
    }

    public Language(String name) {
        this.name = name;
        this.films = new ArrayList<>();
        this.lastUpdate = LocalDateTime.now();
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
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

    public List<Film> getFilms() {
        return films;
    }

    public void setFilms(List<Film> films) {
        this.films = films;
    }
}
