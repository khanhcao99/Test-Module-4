package com.example.module4.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private double area;

    private int population;

    private double gdp;

    private String description;

    @ManyToOne
    private Country country;
}
