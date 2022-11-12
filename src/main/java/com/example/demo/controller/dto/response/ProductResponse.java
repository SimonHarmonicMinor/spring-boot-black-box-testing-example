package com.example.demo.controller.dto.response;

import com.example.demo.domain.Product;

public record ProductResponse(long id, Product.Type type, int quantity) {
    public ProductResponse(Product product) {
        this(
            product.getId(),
            product.getType(),
            product.getQuantity()
        );
    }
}
