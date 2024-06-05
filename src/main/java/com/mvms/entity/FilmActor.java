package com.mvms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "film_actor")
public class FilmActor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "film_id", referencedColumnName = "film_id")
    private Film film;

    @ManyToOne
    @JoinColumn(name = "actor_id", referencedColumnName = "actor_id")
    private Actor actor;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    public FilmActor() {
        this.lastUpdate = LocalDateTime.now();
    }

    public FilmActor(Film film, Actor actor) {
        this.film = film;
        this.actor = actor;
        this.lastUpdate = LocalDateTime.now();
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
