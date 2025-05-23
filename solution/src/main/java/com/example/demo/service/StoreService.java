package com.example.demo.service;

import com.example.demo.dto.store.StoreRequest;
import com.example.demo.dto.store.StoreResponse;
import com.example.demo.dto.storeItem.StoreItemResponse;
import com.example.demo.dto.itemDiscount.ItemDiscountResponse;
import com.example.demo.dto.priceHistory.PriceHistoryResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    private final StoreRepo storeRepository;
    private final ItemRepo itemRepository;
    private final StoreItemRepo storeItemRepository;
    private final ItemDiscountRepo discountRepository;
    private final PriceHistoryRepo historyRepository;

    public StoreService(StoreRepo storeRepository, ItemRepo itemRepository,
                        StoreItemRepo storeItemRepository, ItemDiscountRepo discountRepository,
                        PriceHistoryRepo historyRepository) {
        this.storeRepository = storeRepository;
        this.itemRepository = itemRepository;
        this.storeItemRepository = storeItemRepository;
        this.discountRepository = discountRepository;
        this.historyRepository = historyRepository;
    }


    /**
     * Creates a new store, along with associated store items, discounts, and price histories
     *
     * @param request the store request containing company name, store items, discounts, and price history details
     * @return the created store as a response DTO
     * @throws NotFoundException if any store item, discount, or price history reference is not found
     */
    @Transactional
    public StoreResponse createStore(StoreRequest request) {
        Store store = new Store();
        store.setCompanyName(request.companyName());
        Store savedStore = storeRepository.save(store);

        List<StoreItem> storeItems = request.storeItems().stream()
                .map(itemReq -> {
                    Item item = itemRepository.findById(itemReq.itemId())
                            .orElseThrow(() -> new NotFoundException("Item not found"));
                    StoreItem storeItem = new StoreItem();
                    storeItem.setStore(savedStore);
                    storeItem.setItem(item);
                    storeItem.setUnits(itemReq.units());
                    storeItem.setCurrency(itemReq.currency());
                    return storeItemRepository.save(storeItem);
                })
                .collect(Collectors.toList());

        List<ItemDiscount> discounts = request.discounts().stream()
                .map(discountReq -> {
                    StoreItem storeItem = storeItemRepository.findById(discountReq.storeItemId())
                            .orElseThrow(() -> new NotFoundException("StoreItem not found"));
                    ItemDiscount discount = new ItemDiscount();
                    discount.setStoreItem(storeItem);
                    discount.setStore(savedStore);
                    discount.setOldPrice(discountReq.oldPrice());
                    discount.setDiscountPercentage(discountReq.discountPercentage());
                    discount.setStartDate(discountReq.startDate());
                    discount.setEndDate(discountReq.endDate());
                    return discountRepository.save(discount);
                })
                .collect(Collectors.toList());

        List<PriceHistory> priceHistories = request.priceHistory().stream()
                .map(historyReq -> {
                    StoreItem storeItem = storeItemRepository.findById(historyReq.storeItemId())
                            .orElseThrow(() -> new NotFoundException("StoreItem not found"));
                    PriceHistory history = new PriceHistory();
                    history.setStoreItem(storeItem);
                    history.setStore(savedStore);
                    history.setDate(historyReq.date());
                    history.setPrice(historyReq.price());
                    return historyRepository.save(history);
                })
                .collect(Collectors.toList());

        savedStore.setStoreItems(storeItems);
        savedStore.setDiscounts(discounts);
        savedStore.setPriceHistoryEntries(priceHistories);

        return convertToResponse(savedStore);
    }

    private StoreResponse convertToResponse(Store store) {
        List<StoreItemResponse> storeItems = store.getStoreItems().stream()
                .map(this::convertStoreItemToResponse)
                .collect(Collectors.toList());

        List<ItemDiscountResponse> discounts = store.getDiscounts().stream()
                .map(this::convertDiscountToResponse)
                .collect(Collectors.toList());

        List<PriceHistoryResponse> priceHistory = store.getPriceHistoryEntries().stream()
                .map(this::convertHistoryToResponse)
                .collect(Collectors.toList());

        return new StoreResponse(
                store.getId(),
                store.getCompanyName(),
                storeItems,
                discounts,
                priceHistory
        );
    }

    private StoreItemResponse convertStoreItemToResponse(StoreItem storeItem) {
        return new StoreItemResponse(
                storeItem.getId(),
                storeItem.getStore().getId(),
                storeItem.getItem().getId(),
                storeItem.getTotalPrice(),
                storeItem.getUnits(),
                storeItem.getCurrency()
        );
    }

    private ItemDiscountResponse convertDiscountToResponse(ItemDiscount discount) {
        Long itemId = discount.getStoreItem().getItem().getId();
        return new ItemDiscountResponse(
                discount.getId(),
                discount.getStoreItem().getId(),
                discount.getStore().getId(),
                discount.getOldPrice(),
                discount.getDiscountPercentage(),
                discount.getStartDate(),
                discount.getEndDate(),
                itemId
        );
    }

    private PriceHistoryResponse convertHistoryToResponse(PriceHistory history) {
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
     * Retrieves a store by its ID
     *
     * @param id the ID of the store to retrieve
     * @return the store as a response DTO
     * @throws NotFoundException if the store with the specified ID is not found
     */
    public StoreResponse getStoreById(Long id) {
        Store store = storeRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Store not found"));
        return convertToResponse(store);
    }


    /**
     * Updates an existing store with a new company name
     *
     * @param id the ID of the store to update
     * @param companyName the new company name for the store
     * @return the updated store as a response DTO
     * @throws NotFoundException if the store with the specified ID is not found
     */
    @Transactional
    public StoreResponse updateStore(Long id, String companyName) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Store not found"));

        store.setCompanyName(companyName);
        Store updatedStore = storeRepository.save(store);

        return convertToResponse(updatedStore);
    }


    /**
     * Deletes a store by its ID
     *
     * @param id the ID of the store to delete
     * @return the deleted store as a response DTO
     * @throws NotFoundException if the store with the specified ID is not found
     */
    @Transactional
    public StoreResponse deleteStore(Long id) {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Store not found"));

        storeRepository.delete(store);
        return convertToResponse(store);
    }

}