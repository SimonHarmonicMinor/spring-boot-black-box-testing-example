package com.example.demo.controller.dto.response;

import com.example.demo.domain.Fridge;

import java.util.List;

public record FridgeResponse(long id, String name, List<ProductResponse> products) {
    public FridgeResponse(Fridge fridge) {
        this(
            fridge.getId(),
            fridge.getName(),
            fridge.getProducts()
                .stream()
                .map(ProductResponse::new)
                .toList()
        );
    }
}
