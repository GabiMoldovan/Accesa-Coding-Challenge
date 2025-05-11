package com.example.demo.service;

import com.example.demo.dto.basket.BasketRequest;
import com.example.demo.dto.basket.BasketResponse;
import com.example.demo.dto.basketItem.BasketItemRequest;
import com.example.demo.dto.basketItem.BasketItemResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BasketService {
    private final BasketRepo basketRepository;
    private final UserRepo userRepository;
    private final StoreRepo storeRepository;
    private final StoreItemRepo storeItemRepository;

    public BasketService(BasketRepo basketRepository, UserRepo userRepository,
                         StoreRepo storeRepository, StoreItemRepo storeItemRepository) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.storeItemRepository = storeItemRepository;
    }

    @Transactional
    public BasketResponse createBasket(BasketRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new NotFoundException("Store not found"));

        Basket basket = new Basket();
        basket.setUser(user);
        basket.setStore(store);
        Basket savedBasket = basketRepository.save(basket);

        for (BasketItemRequest itemReq : request.items()) {
            StoreItem storeItem = storeItemRepository.findById(itemReq.storeItemId())
                    .orElseThrow(() -> new NotFoundException("StoreItem not found"));
            BasketItem basketItem = new BasketItem(storeItem, itemReq.quantity());
            basket.addItem(basketItem);
        }

        return convertToResponse(basketRepository.save(savedBasket));
    }

    public BasketResponse getBasketById(Long id) {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Basket not found"));
        return convertToResponse(basket);
    }

    @Transactional
    public void deleteBasket(Long id) {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Basket not found"));
        basketRepository.delete(basket);
    }

    private BasketResponse convertToResponse(Basket basket) {
        List<BasketItemResponse> items = basket.getItems().stream()
                .map(this::convertItemToResponse)
                .collect(Collectors.toList());

        return new BasketResponse(
                basket.getId(),
                basket.getUser().getId(),
                basket.getStore().getId(),
                items
        );
    }

    private BasketItemResponse convertItemToResponse(BasketItem item) {
        return new BasketItemResponse(
                item.getId(),
                item.getBasket().getId(),
                item.getStoreItem().getId(),
                item.getQuantity(),
                item.getPriceAtAddition()
        );
    }
}