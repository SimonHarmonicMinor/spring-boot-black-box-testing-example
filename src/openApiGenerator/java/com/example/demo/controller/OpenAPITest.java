package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Path;

import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
    webEnvironment = RANDOM_PORT
)
@AutoConfigureTestDatabase
@ActiveProfiles("qa")
public class OpenAPITest {
    @Autowired
    private TestRestTemplate rest;

    @Test
    @SneakyThrows
    void generateOpenApiSpec() {
        final var response = rest.getForEntity("/v3/api-docs", String.class);
        assertTrue(response.getStatusCode().is2xxSuccessful(), "Unexpected status code: " + response.getStatusCode());
        // the specification will be written to 'build/classes/openApiGenerator/open-api.json'
        Files.writeString(
            Path.of(getClass().getResource("/").getPath(), "open-api.json"),
            response.getBody()
        );
    }
}
