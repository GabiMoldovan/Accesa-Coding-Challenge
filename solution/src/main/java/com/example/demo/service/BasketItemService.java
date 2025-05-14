package com.example.demo.service;

import com.example.demo.dto.basketItem.BasketItemRequest;
import com.example.demo.dto.basketItem.BasketItemResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.BasketItem;
import com.example.demo.model.Basket;
import com.example.demo.model.StoreItem;
import com.example.demo.repository.BasketItemRepo;
import com.example.demo.repository.BasketRepo;
import com.example.demo.repository.StoreItemRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BasketItemService {
    private final BasketItemRepo basketItemRepository;
    private final BasketRepo basketRepository;
    private final StoreItemRepo storeItemRepository;


    public BasketItemService(BasketItemRepo basketItemRepository,
                             BasketRepo basketRepository,
                             StoreItemRepo storeItemRepository) {
        this.basketItemRepository = basketItemRepository;
        this.basketRepository = basketRepository;
        this.storeItemRepository = storeItemRepository;
    }


    /**
     * Adds a new item to the basket based on the request data
     *
     * Finds the basket and store item by their IDs. If either is not found,
     * a NotFoundException is thrown
     *
     * @param request the request object containing basket ID, store item ID, and quantity
     * @return the created BasketItemResponse with item details
     * @throws NotFoundException if the basket or store item is not found
     */
    @Transactional
    public BasketItemResponse addItemToBasket(BasketItemRequest request) {
        Basket basket = basketRepository.findById(request.basketId())
                .orElseThrow(() -> new NotFoundException("Basket not found"));
        StoreItem storeItem = storeItemRepository.findById(request.storeItemId())
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));

        BasketItem basketItem = new BasketItem();
        basketItem.setBasket(basket);
        basketItem.setStoreItem(storeItem);
        basketItem.setQuantity(request.quantity());
        basketItem.setPriceAtAddition(storeItem.getTotalPrice());

        BasketItem savedItem = basketItemRepository.save(basketItem);
        return convertToResponse(savedItem);
    }

    /**
     * Updates the quantity of an existing item in the basket
     *
     * @param itemId the ID of the basket item to update
     * @param newQuantity the new quantity to set
     * @throws NotFoundException if the basket item is not found
     */
    @Transactional
    public void updateItemQuantity(Long itemId, float newQuantity) {
        BasketItem basketItem = basketItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("BasketItem not found"));
        basketItem.setQuantity(newQuantity);
        basketItemRepository.save(basketItem);
    }

    /**
     * Removes an item from the basket by its ID
     *
     * @param itemId the ID of the basket item to remove
     */
    @Transactional
    public void removeItemFromBasket(Long itemId) {
        basketItemRepository.deleteById(itemId);
    }

    public BasketItemResponse getBasketItemById(Long id) {
        BasketItem basketItem = basketItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("BasketItem not found"));
        return convertToResponse(basketItem);
    }

    private BasketItemResponse convertToResponse(BasketItem basketItem) {
        return new BasketItemResponse(
                basketItem.getId(),
                basketItem.getBasket().getId(),
                basketItem.getStoreItem().getId(),
                basketItem.getQuantity(),
                basketItem.getPriceAtAddition()
        );
    }
}