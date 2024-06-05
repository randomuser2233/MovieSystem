package com.mvms.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long cityId;

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    private Country country;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "last_update", nullable = false)
    private LocalDateTime lastUpdate;

    public City(Long cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.lastUpdate = LocalDateTime.now();
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
