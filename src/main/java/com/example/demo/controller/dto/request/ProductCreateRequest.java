package com.example.demo.controller.dto.request;

import com.example.demo.domain.Product;

public record ProductCreateRequest(String name, Product.Type type, int quantity) {
}
