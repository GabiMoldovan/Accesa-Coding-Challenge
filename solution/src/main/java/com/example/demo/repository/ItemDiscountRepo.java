package com.example.demo.repository;

import com.example.demo.model.ItemDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDiscountRepo extends JpaRepository<ItemDiscount, Long> {
}
