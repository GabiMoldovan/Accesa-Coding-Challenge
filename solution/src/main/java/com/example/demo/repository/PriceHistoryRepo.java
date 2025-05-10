package com.example.demo.repository;

import com.example.demo.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepo extends JpaRepository<PriceHistory, Long> {
}
