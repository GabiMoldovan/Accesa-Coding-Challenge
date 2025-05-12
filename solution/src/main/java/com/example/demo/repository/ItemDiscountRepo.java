package com.example.demo.repository;

import com.example.demo.model.ItemDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemDiscountRepo extends JpaRepository<ItemDiscount, Long> {
    List<ItemDiscount> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDateTime start, LocalDateTime end);
    List<ItemDiscount> findAllByOrderByDiscountPercentageDesc();
    List<ItemDiscount> findAllByStoreItem_Item_Id(Long itemId);
}
