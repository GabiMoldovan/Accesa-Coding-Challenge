package com.example.demo.repository;

import com.example.demo.model.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {
}
