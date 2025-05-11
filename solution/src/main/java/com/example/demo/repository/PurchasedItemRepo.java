package com.example.demo.repository;

import com.example.demo.model.PurchasedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchasedItemRepo extends JpaRepository<PurchasedItem, Long> {
    List<PurchasedItem> findBySpendingId(Long spendingId);
}
