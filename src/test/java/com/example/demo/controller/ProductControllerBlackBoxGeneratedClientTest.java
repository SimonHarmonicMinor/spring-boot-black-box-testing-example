package com.example.demo.controller;

import com.example.demo.generated.ProductCreateRequest;
import com.example.demo.testutil.IntegrationSuite;
import com.example.demo.testutil.TestRestControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import lombok.SneakyThrows;

import static com.example.demo.generated.ProductCreateRequest.TypeEnum.POTATO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("qa")
@Import(TestRestControllers.class)
class ProductControllerBlackBoxGeneratedClientTest extends IntegrationSuite {
    @Autowired
    private TestRestControllers rest;

    @BeforeEach
    @SneakyThrows
    void beforeEach() {
        rest.qaController().clearData();
    }

    @Test
    void shouldUpdateProductQuantity() {
        final var fridgeResponse = assertDoesNotThrow(
            () -> rest.fridgeController()
                .createNewFridge("someFridge")
        );
        final var productResponse = assertDoesNotThrow(
            () -> rest.productController()
                .createNewProduct(
                    fridgeResponse.getId(),
                    new ProductCreateRequest()
                        .quantity(10)
                        .type(POTATO)
                )
        );

        assertDoesNotThrow(
            () -> rest.productController()
                .updateProductQuantity(productResponse.getId(), 20)
        );

        final var updatedProduct = assertDoesNotThrow(
            () -> rest.productController()
                .getProductById(productResponse.getId())
        );
        assertEquals(20, updatedProduct.getQuantity());
    }
}