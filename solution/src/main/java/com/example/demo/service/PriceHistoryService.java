package com.example.demo.service;

import com.example.demo.dto.priceHistory.PriceHistoryRequest;
import com.example.demo.dto.priceHistory.PriceHistoryResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.PriceHistory;
import com.example.demo.model.Store;
import com.example.demo.model.StoreItem;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

@Service
public class PriceHistoryService {
    private final PriceHistoryRepo historyRepository;
    private final StoreItemRepo storeItemRepository;
    private final StoreRepo storeRepository;

    public PriceHistoryService(PriceHistoryRepo historyRepository,
                               StoreItemRepo storeItemRepository, StoreRepo storeRepository) {
        this.historyRepository = historyRepository;
        this.storeItemRepository = storeItemRepository;
        this.storeRepository = storeRepository;
    }

    public PriceHistoryResponse createHistoryEntry(PriceHistoryRequest request) {
        StoreItem storeItem = storeItemRepository.findById(request.storeItemId())
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new NotFoundException("Store not found"));

        PriceHistory history = new PriceHistory();
        history.setStoreItem(storeItem);
        history.setStore(store);
        history.setDate(request.date());
        history.setPrice(request.price());

        PriceHistory savedHistory = historyRepository.save(history);
        return convertToResponse(savedHistory);
    }

    private PriceHistoryResponse convertToResponse(PriceHistory history) {
        return new PriceHistoryResponse(
                history.getId(),
                history.getStoreItem().getId(),
                history.getStore().getId(),
                history.getDate(),
                history.getPrice()
        );
    }
}