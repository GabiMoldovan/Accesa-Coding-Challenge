package com.example.demo.repository;

import com.example.demo.model.Store;
import com.example.demo.model.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store, Long> {
}
