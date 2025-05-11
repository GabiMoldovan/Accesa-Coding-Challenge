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
        basketItem.setPriceAtAddition(storeItem.getPricePerUnit());

        BasketItem savedItem = basketItemRepository.save(basketItem);
        return convertToResponse(savedItem);
    }

    @Transactional
    public void updateItemQuantity(Long itemId, float newQuantity) {
        BasketItem basketItem = basketItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("BasketItem not found"));
        basketItem.setQuantity(newQuantity);
        basketItemRepository.save(basketItem);
    }

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