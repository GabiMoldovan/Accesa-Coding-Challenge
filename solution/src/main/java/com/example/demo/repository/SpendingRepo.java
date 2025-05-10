package com.example.demo.repository;

import com.example.demo.model.Spending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpendingRepo extends JpaRepository<Spending, Long> {
    // This repo controlls the PurchasedItem as well
}
