package com.example.demo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PACKAGE;

@Entity
@Table(name = "product")
@Getter
@Setter(PACKAGE)
public class Product {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull(message = "Product type cannot be null")
    @Enumerated(STRING)
    @Column(updatable = false)
    private Type type;

    @Min(value = 1, message = "Product quantity cannot be less than {value} but provided ${validatedValue}")
    private int quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "fridge_id", updatable = false)
    private Fridge fridge;

    public void updateQuantity(int quantity) {
        setQuantity(quantity);
    }

    public static Product newProduct(Type type, int quantity, Fridge fridge) {
        final var product = new Product();
        product.setType(type);
        product.setQuantity(quantity);
        product.setFridge(fridge);
        return product;
    }

    public enum Type {
        POTATO, ONION, CARROT
    }
}
