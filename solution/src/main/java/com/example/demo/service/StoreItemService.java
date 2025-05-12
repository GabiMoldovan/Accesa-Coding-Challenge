package com.example.demo.service;

import com.example.demo.dto.storeItem.StoreItemRequest;
import com.example.demo.dto.storeItem.StoreItemResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.StoreItem;
import com.example.demo.model.Store;
import com.example.demo.model.Item;
import com.example.demo.repository.StoreItemRepo;
import com.example.demo.repository.StoreRepo;
import com.example.demo.repository.ItemRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreItemService {
    private final StoreItemRepo storeItemRepository;
    private final StoreRepo storeRepository;
    private final ItemRepo itemRepository;

    public StoreItemService(StoreItemRepo storeItemRepository,
                            StoreRepo storeRepository,
                            ItemRepo itemRepository) {
        this.storeItemRepository = storeItemRepository;
        this.storeRepository = storeRepository;
        this.itemRepository = itemRepository;
    }

    @Transactional
    public StoreItemResponse createStoreItem(StoreItemRequest request) {
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new NotFoundException("Store not found"));
        Item item = itemRepository.findById(request.itemId())
                .orElseThrow(() -> new NotFoundException("Item not found"));

        StoreItem storeItem = new StoreItem();
        storeItem.setStore(store);
        storeItem.setItem(item);
        storeItem.setTotalPrice(request.totalPrice());
        storeItem.setUnits(request.units());
        storeItem.setCurrency(request.currency());

        StoreItem savedItem = storeItemRepository.save(storeItem);
        return convertToResponse(savedItem);
    }

    public StoreItemResponse getStoreItemById(Long id) {
        StoreItem storeItem = storeItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));
        return convertToResponse(storeItem);
    }

    public List<StoreItemResponse> getAllByStoreId(Long storeId) {
        return storeItemRepository.findByStoreId(storeId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public StoreItemResponse updateStoreItem(Long id, StoreItemRequest request) {
        StoreItem storeItem = storeItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));
        storeItem.setTotalPrice(request.totalPrice());
        storeItem.setUnits(request.units());
        storeItem.setCurrency(request.currency());

        return convertToResponse(storeItemRepository.save(storeItem));
    }

    @Transactional
    public StoreItemResponse deleteStoreItem(Long id) {
        StoreItem storeItem = storeItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));

        storeItemRepository.delete(storeItem);
        return convertToResponse(storeItem);
    }


    private StoreItemResponse convertToResponse(StoreItem storeItem) {
        return new StoreItemResponse(
                storeItem.getId(),
                storeItem.getStore().getId(),
                storeItem.getItem().getId(),
                storeItem.getTotalPrice(),
                storeItem.getUnits(),
                storeItem.getCurrency()
        );
    }
}