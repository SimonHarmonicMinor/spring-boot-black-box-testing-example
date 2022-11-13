package com.example.demo.controller;

import com.example.demo.controller.dto.request.ProductCreateRequest;
import com.example.demo.controller.dto.response.FridgeResponse;
import com.example.demo.controller.dto.response.ProductResponse;
import com.example.demo.testutil.IntegrationSuite;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static com.example.demo.domain.Product.Type.POTATO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("qa")
class ProductControllerBlackBoxTest extends IntegrationSuite {
    @Autowired
    private TestRestTemplate rest;

    @BeforeEach
    void beforeEach() {
        rest.delete("/api/qa/clearData");
    }

    @Test
    void shouldUpdateProductQuantity() {
        // create new Fridge
        final var fridgeResponse =
            rest.postForEntity("/api/fridge?name={fridgeName}", null, FridgeResponse.class, Map.of(
                "fridgeName", "someFridge"
            ));
        assertTrue(fridgeResponse.getStatusCode().is2xxSuccessful(), "Error during creating new Fridge: " + fridgeResponse.getStatusCode());
        // create new Product
        final var productResponse = rest.postForEntity(
            "/api/product/fridge/{fridgeId}",
            new ProductCreateRequest(
                POTATO,
                10
            ),
            ProductResponse.class,
            Map.of(
                "fridgeId", fridgeResponse.getBody().id()
            )
        );
        assertTrue(productResponse.getStatusCode().is2xxSuccessful(), "Error during creating new Product: " + productResponse.getStatusCode());

        // call the API that should be tested
        assertDoesNotThrow(
            () -> rest.put("/api/product/{productId}?newQuantity={newQuantity}",
                null,
                Map.of(
                    "productId", productResponse.getBody().id(),
                    "newQuantity", 20
                ))
        );

        // get the updated Product by id
        final var updatedProductResponse = rest.getForEntity(
            "/api/product/{productId}",
            ProductResponse.class,
            Map.of(
                "productId", productResponse.getBody().id()
            )
        );
        assertTrue(
            updatedProductResponse.getStatusCode().is2xxSuccessful(),
            "Error during retrieving Product by id: " + updatedProductResponse.getStatusCode()
        );
        // check that the quantity has been changed
        assertEquals(20, updatedProductResponse.getBody().quantity());
    }
}