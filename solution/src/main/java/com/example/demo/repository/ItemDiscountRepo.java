package com.example.demo.repository;

import com.example.demo.model.ItemDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemDiscountRepo extends JpaRepository<ItemDiscount, Long> {
    List<ItemDiscount> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(LocalDateTime start, LocalDateTime end);
    List<ItemDiscount> findAllByOrderByDiscountPercentageDesc();
    List<ItemDiscount> findAllByStoreItem_Item_Id(Long itemId);
    List<ItemDiscount> findByEndDateLessThan(LocalDateTime end);

    @Query("SELECT d FROM ItemDiscount d JOIN FETCH d.storeItem si JOIN FETCH si.item WHERE d.startDate <= :start AND d.endDate >= :end")
    List<ItemDiscount> findActiveDiscountsWithItems(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
