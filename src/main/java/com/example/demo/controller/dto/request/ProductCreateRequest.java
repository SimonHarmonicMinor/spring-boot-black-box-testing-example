package com.example.demo.controller.dto.request;

import com.example.demo.domain.Product;

public record ProductCreateRequest(Product.Type type, int quantity) {
}
