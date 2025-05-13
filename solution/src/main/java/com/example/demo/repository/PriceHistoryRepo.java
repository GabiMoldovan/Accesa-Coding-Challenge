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

    @Query("SELECT ph FROM PriceHistory ph " +
            "JOIN ph.storeItem si " +
            "JOIN si.item i " +
            "WHERE (:storeId IS NULL OR ph.store.id = :storeId) " +
            "AND (:category IS NULL OR i.category = :category) " +
            "AND (:brand IS NULL OR i.brand = :brand)")
    List<PriceHistory> findFilteredByStoreCategoryBrand(
            @Param("storeId") Long storeId,
            @Param("category") String category,
            @Param("brand") String brand);

    @Query("SELECT ph FROM PriceHistory ph WHERE ph.storeItem.item.id = :itemId AND ph.store.id = :storeId")
    List<PriceHistory> findByItemIdAndStoreId(@Param("itemId") Long itemId, @Param("storeId") Long storeId);

}
