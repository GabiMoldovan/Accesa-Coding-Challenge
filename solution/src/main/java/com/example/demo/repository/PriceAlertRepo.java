package com.example.demo.repository;

import com.example.demo.model.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceAlertRepo extends JpaRepository<PriceAlert, Long> {
    List<PriceAlert> findByUserId(Long userId);
}
