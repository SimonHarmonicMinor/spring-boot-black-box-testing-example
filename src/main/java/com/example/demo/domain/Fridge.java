package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PACKAGE;

@Entity
@Table(name = "fridge")
@Getter
@Setter(PACKAGE)
public class Fridge {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull(message = "Fridge name cannot be null")
    private String name;

    @OneToMany(fetch = LAZY, mappedBy = "fridge")
    private List<Product> products = new ArrayList<>();

    public static Fridge newFridge(String name) {
        final var fridge = new Fridge();
        fridge.setName(name);
        return fridge;
    }
}
