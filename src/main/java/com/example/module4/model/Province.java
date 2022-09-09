package com.example.module4.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "province")
@Getter
@Setter
@NoArgsConstructor

public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private double area;

    @NotNull
    private int population;

    @NotNull
    private double gdp;

    @NotNull
    @NotBlank
    private String description;

    @ManyToOne
    private Country country;
}
