package com.example.demo.testutil;

import com.example.demo.generated.ApiClient;
import com.example.demo.generated.FridgeControllerApi;
import com.example.demo.generated.ProductControllerApi;
import com.example.demo.generated.QaControllerApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.core.env.Environment;

@TestComponent
public class TestRestControllers {
    @Autowired
    private Environment environment;

    public FridgeControllerApi fridgeController() {
        return new FridgeControllerApi(newApiClient());
    }

    public ProductControllerApi productController() {
        return new ProductControllerApi(newApiClient());
    }

    public QaControllerApi qaController() {
        return new QaControllerApi(newApiClient());
    }

    private ApiClient newApiClient() {
        final var apiClient = new ApiClient();
        apiClient.setBasePath("http://localhost:" + environment.getProperty("local.server.port", Integer.class));
        return apiClient;
    }
}
