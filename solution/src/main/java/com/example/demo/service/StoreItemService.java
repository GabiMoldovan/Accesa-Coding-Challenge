package com.example.demo.service;

import com.example.demo.dto.storeItem.StoreItemRequest;
import com.example.demo.dto.storeItem.StoreItemResponse;
import com.example.demo.dto.storeItemBestValueResponse.StoreItemBestValueResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.StoreItem;
import com.example.demo.model.Store;
import com.example.demo.model.Item;
import com.example.demo.repository.StoreItemRepo;
import com.example.demo.repository.StoreRepo;
import com.example.demo.repository.ItemRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreItemService {
    private final StoreItemRepo storeItemRepository;
    private final StoreRepo storeRepository;
    private final ItemRepo itemRepository;
    private final PriceHistoryService priceHistoryService;

    public StoreItemService(StoreItemRepo storeItemRepository,
                            StoreRepo storeRepository,
                            ItemRepo itemRepository,
                            PriceHistoryService priceHistoryService) {
        this.storeItemRepository = storeItemRepository;
        this.storeRepository = storeRepository;
        this.itemRepository = itemRepository;
        this.priceHistoryService = priceHistoryService;
    }


    /**
     * Creates a new store item and registers its price in the price history
     *
     * @param request the store item request containing store ID, item ID, total price, units, and currency
     * @return the created store item as a response DTO
     * @throws NotFoundException if the store or item associated with the request is not found
     */
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

        // Register the price as well in the price history when the store item is created
        priceHistoryService.recordPriceChange(
                item.getId(),
                store.getId(),
                request.totalPrice()
        );

        return convertToResponse(savedItem);
    }


    /**
     * Retrieves a store item by its ID
     *
     * @param id the ID of the store item to retrieve
     * @return the store item as a response DTO
     * @throws NotFoundException if the store item with the specified ID is not found
     */
    public StoreItemResponse getStoreItemById(Long id) {
        StoreItem storeItem = storeItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));
        return convertToResponse(storeItem);
    }


    /**
     * Retrieves all store items associated with a specific store
     *
     * @param storeId the ID of the store for which to retrieve store items
     * @return a list of store items as response DTOs
     */
    public List<StoreItemResponse> getAllByStoreId(Long storeId) {
        return storeItemRepository.findByStoreId(storeId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    /**
     * Updates an existing store item and registers any price changes in the price history
     *
     * @param id the ID of the store item to update
     * @param request the store item request containing updated total price, units, and currency
     * @return the updated store item as a response DTO
     * @throws NotFoundException if the store item with the specified ID is not found
     */
    @Transactional
    public StoreItemResponse updateStoreItem(Long id, StoreItemRequest request) {
        StoreItem storeItem = storeItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));

        // saving the old price to compare it to the patched store item
        float oldPrice = storeItem.getTotalPrice();
        float newPrice = request.totalPrice();

        // register the price history entry if the price changed
        if (oldPrice != newPrice) {
            priceHistoryService.recordPriceChange(
                    storeItem.getItem().getId(),
                    storeItem.getStore().getId(),
                    newPrice
            );
        }

        storeItem.setTotalPrice(request.totalPrice());
        storeItem.setUnits(request.units());
        storeItem.setCurrency(request.currency());

        StoreItem updatedItem = storeItemRepository.save(storeItem);
        return convertToResponse(updatedItem);
    }


    /**
     * Deletes a store item by its ID
     *
     * @param id the ID of the store item to delete
     * @return the deleted store item as a response DTO
     * @throws NotFoundException if the store item with the specified ID is not found
     */
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


    /**
     * Finds the store item with the best value per unit for a specific store item
     * (even if it is in another store)
     *
     * @param storeItemId the ID of the store item to find the best value for
     * @return the store item with the best value per unit as a response DTO
     * @throws NotFoundException if the store item or its related items cannot be found
     */
    public StoreItemBestValueResponse findBestValuePerUnit(Long storeItemId) {
        // Finding the original StoreItem
        StoreItem originalItem = storeItemRepository.findById(storeItemId)
                .orElseThrow(() -> new NotFoundException("StoreItem doesn't exist"));

        // Extracts all the StoreItems that belong to the Item
        List<StoreItem> allItemsForProduct = storeItemRepository.findByItemId(originalItem.getItem().getId());

        //  Calculates the price per unit for each one and returns the min
        StoreItem bestItem = allItemsForProduct.stream()
                .min(Comparator.comparingDouble(item ->
                        item.getTotalPrice() / item.getUnits()
                ))
                .orElseThrow(() -> new NotFoundException("There are no storeItems for this Item"));

        return new StoreItemBestValueResponse(
                bestItem.getId(),
                bestItem.getStore().getId(),
                bestItem.getItem().getId(),
                bestItem.getTotalPrice(),
                bestItem.getUnits(),
                bestItem.getTotalPrice() / bestItem.getUnits(),
                bestItem.getCurrency()
        );
    }
}