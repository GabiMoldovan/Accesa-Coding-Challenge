package com.example.demo.service;

import com.example.demo.dto.priceHistory.PriceHistoryRequest;
import com.example.demo.dto.priceHistory.PriceHistoryResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.PriceHistory;
import com.example.demo.model.Store;
import com.example.demo.model.StoreItem;
import com.example.demo.model.enums.Category;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
                history.getPrice(),
                history.getStoreItem().getItem().getCategory().name(),
                history.getStoreItem().getItem().getBrand()
        );
    }


    /**
     * Records a price change for a store item at a specific store
     *
     * This method is transactional and adds a new price history entry with the current date and time
     *
     * @param itemId the ID of the item
     * @param storeId the ID of the store
     * @param newPrice the new price of the item
     * @throws NotFoundException if the store item is not found
     */
    @Transactional
    public void recordPriceChange(Long itemId, Long storeId, float newPrice) {
        StoreItem storeItem = storeItemRepository.findByItemIdAndStoreId(itemId, storeId)
                .orElseThrow(() -> new NotFoundException("StoreItem not found for item: " + itemId + " and store: " + storeId));

        PriceHistory history = new PriceHistory();
        history.setStoreItem(storeItem);
        history.setStore(storeItem.getStore());
        history.setDate(LocalDateTime.now());
        history.setPrice(newPrice);

        historyRepository.save(history);
    }


    /**
     * Retrieves the price history for a specific store, category, and brand
     *
     * @param storeId the ID of the store
     * @param category the category of the items
     * @param brand the brand of the items
     * @return a list of price history entries matching the filter criteria as response DTOs
     */
    public List<PriceHistoryResponse> getFilteredPriceHistoryByStoreCategoryBrand(Long storeId, Category category, String brand) {
        return historyRepository.findFilteredByStoreCategoryBrand(storeId, category, brand)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves all price history entries for a specific item at a given store
     *
     * @param itemId the ID of the item
     * @param storeId the ID of the store
     * @return a list of price history entries for the item at the store as response DTOs
     * @throws NotFoundException if no price history is found for the item at the store
     */
    public List<PriceHistoryResponse> getAllPriceHistoryForItemAtStore(Long itemId, Long storeId) {
        List<PriceHistory> historyList = historyRepository.findByItemIdAndStoreId(itemId, storeId);

        if (historyList.isEmpty()) {
            throw new NotFoundException("No price history found for item " + itemId + " at store " + storeId);
        }

        return historyList.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


}