package com.example.demo.repository;

import com.example.demo.model.BasketItem;
import com.example.demo.model.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BasketItemRepo extends JpaRepository<BasketItem, Long> {
    List<BasketItem> findByStoreItem(StoreItem storeItem);
}
