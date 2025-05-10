package com.example.demo.repository;

import com.example.demo.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepo extends JpaRepository<Basket, Long> {
    // This repo controlls the BasketItem as well
}
