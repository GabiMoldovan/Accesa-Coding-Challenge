package com.example.demo.repository;

import com.example.demo.model.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreItemRepo extends JpaRepository<StoreItem, Long> {
    List<StoreItem> findByStoreId(Long storeId);
}
