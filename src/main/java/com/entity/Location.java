package com.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name = "Locations")
@Getter
@NoArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @Setter
    private int id;
    @Column(name = "Name")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id")
    private User user;
    @Column(name = "Latitude")
    private BigDecimal latitude;
    @Column(name = "Longitude")
    private BigDecimal longitude;

    public Location(String name, User user, BigDecimal latitude, BigDecimal longitude) {
        this.name = name;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
