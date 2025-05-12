package com.example.demo.repository;

import com.example.demo.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceHistoryRepo extends JpaRepository<PriceHistory, Long> {
    @Query("SELECT ph FROM PriceHistory ph WHERE " +
            "(:itemId IS NULL OR ph.storeItem.item.id = :itemId) AND " +
            "(:storeId IS NULL OR ph.store.id = :storeId) AND " +
            "(:startDate IS NULL OR ph.date >= :startDate) AND " +
            "(:endDate IS NULL OR ph.date <= :endDate)")
    List<PriceHistory> findFiltered(@Param("itemId") Long itemId,
                                    @Param("storeId") Long storeId,
                                    @Param("startDate") LocalDateTime startDate,
                                    @Param("endDate") LocalDateTime endDate);
}
