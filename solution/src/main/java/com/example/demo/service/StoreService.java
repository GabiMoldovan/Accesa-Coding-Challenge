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
                    storeItem.setPricePerUnit(itemReq.pricePerUnit());
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
                storeItem.getPricePerUnit(),
                storeItem.getUnits(),
                storeItem.getCurrency(),
                storeItem.getTotalPrice()
        );
    }

    private ItemDiscountResponse convertDiscountToResponse(ItemDiscount discount) {
        return new ItemDiscountResponse(
                discount.getId(),
                discount.getStoreItem().getId(),
                discount.getStore().getId(),
                discount.getOldPrice(),
                discount.getDiscountPercentage(),
                discount.getStartDate(),
                discount.getEndDate()
        );
    }

    private PriceHistoryResponse convertHistoryToResponse(PriceHistory history) {
        return new PriceHistoryResponse(
                history.getId(),
                history.getStoreItem().getId(),
                history.getStore().getId(),
                history.getDate(),
                history.getPrice()
        );
    }
}