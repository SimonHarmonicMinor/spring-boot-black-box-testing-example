package com.example.demo.repo;

import com.example.demo.domain.Fridge;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {
}
