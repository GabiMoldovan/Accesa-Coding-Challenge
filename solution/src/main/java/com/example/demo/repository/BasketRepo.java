package com.example.demo.repository;

import com.example.demo.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepo extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUserIdAndStoreId(Long userId, Long storeId);
    List<Basket> findByUserId(Long userId);
}
