package com.example.demo.controller;

import com.example.demo.controller.dto.request.ProductCreateRequest;
import com.example.demo.controller.dto.response.ProductResponse;
import com.example.demo.domain.Product;
import com.example.demo.repo.FridgeRepository;
import com.example.demo.repo.ProductRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final FridgeRepository fridgeRepository;
    private final ProductRepository productRepository;

    @PostMapping("/api/product/fridge/{fridgeId}")
    @Transactional
    public ProductResponse createNewProduct(
        @PathVariable Long fridgeId,
        @RequestBody ProductCreateRequest request
    ) {
        final var fridge = fridgeRepository.findById(fridgeId).orElseThrow();
        return new ProductResponse(
            productRepository.save(
                Product.newProduct(
                    request.type(),
                    request.quantity(),
                    fridge
                )
            )
        );
    }

    @PutMapping("/api/product/{productId}")
    @Transactional
    public ProductResponse updateProductQuantity(
        @PathVariable Long productId,
        @RequestParam int newQuantity
    ) {
        final var product = productRepository.findById(productId).orElseThrow();
        product.updateQuantity(newQuantity);
        return new ProductResponse(product);
    }

    @DeleteMapping("/api/product/{productId}")
    @Transactional
    public void deleteProduct(@PathVariable Long productId) {
        productRepository.deleteById(productId);
    }
}
