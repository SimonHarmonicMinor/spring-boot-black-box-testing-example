package com.example.demo.controller;

import com.example.demo.repo.FridgeRepository;
import com.example.demo.repo.ProductRepository;

import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Profile("qa")
public class QAController {
    private final FridgeRepository fridgeRepository;
    private final ProductRepository productRepository;

    @DeleteMapping("/api/qa/clearData")
    @Transactional
    public void clearData() {
        productRepository.deleteAllInBatch();
        fridgeRepository.deleteAllInBatch();
    }
}
