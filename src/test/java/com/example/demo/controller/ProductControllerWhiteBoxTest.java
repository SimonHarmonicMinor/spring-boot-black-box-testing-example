package com.example.demo.controller;

import com.example.demo.domain.Fridge;
import com.example.demo.domain.Product;
import com.example.demo.repo.FridgeRepository;
import com.example.demo.repo.ProductRepository;
import com.example.demo.testutil.IntegrationSuite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.Map;

import static com.example.demo.domain.Product.Type.POTATO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductControllerWhiteBoxTest extends IntegrationSuite {
    @Autowired
    private FridgeRepository fridgeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private TestRestTemplate rest;

    @BeforeEach
    void beforeEach() {
        productRepository.deleteAllInBatch();
        fridgeRepository.deleteAllInBatch();
    }

    @Test
    void shouldUpdateProductQuantity() {
        final var fridge = fridgeRepository.save(Fridge.newFridge("someFridge"));
        final var productId = productRepository.save(Product.newProduct(POTATO, 10, fridge)).getId();

        assertDoesNotThrow(() -> rest.put("/api/product/{productId}?newQuantity={newQuantity}", null, Map.of(
            "productId", productId,
            "newQuantity", 20
        )));

        final var product = productRepository.findById(productId).orElseThrow();
        assertEquals(20, product.getQuantity());
    }
}