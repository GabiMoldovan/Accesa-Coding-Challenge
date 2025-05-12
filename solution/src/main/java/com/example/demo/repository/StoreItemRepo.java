package com.example.demo.repository;

import com.example.demo.model.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreItemRepo extends JpaRepository<StoreItem, Long> {
    List<StoreItem> findByStoreId(Long storeId);

    @Query("SELECT si FROM StoreItem si WHERE si.item.id = :itemId AND si.store.id = :storeId")
    Optional<StoreItem> findByItemIdAndStoreId(@Param("itemId") Long itemId, @Param("storeId") Long storeId);

    @Query("SELECT si FROM StoreItem si WHERE si.item.id = :itemId")
    List<StoreItem> findByItemId(@Param("itemId") Long itemId);
}
