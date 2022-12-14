package com.example.demo.controller;

import com.example.demo.controller.dto.response.FridgeResponse;
import com.example.demo.domain.Fridge;
import com.example.demo.repo.FridgeRepository;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FridgeController {
    private final FridgeRepository fridgeRepository;

    @GetMapping("/api/fridge/{id}")
    @Transactional
    public FridgeResponse getFridgeById(@PathVariable Long id) {
        return fridgeRepository.findById(id)
            .map(FridgeResponse::new)
            .orElseThrow();
    }

    @PostMapping("/api/fridge")
    @Transactional
    public FridgeResponse createNewFridge(@RequestParam String name) {
        return new FridgeResponse(
            fridgeRepository.save(
                Fridge.newFridge(name)
            )
        );
    }
}
