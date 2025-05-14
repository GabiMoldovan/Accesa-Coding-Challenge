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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BasketService {
    private final BasketRepo basketRepository;
    private final UserRepo userRepository;
    private final StoreRepo storeRepository;
    private final StoreItemRepo storeItemRepository;
    private final BasketItemRepo basketItemRepository;

    public BasketService(BasketRepo basketRepository, UserRepo userRepository,
                         StoreRepo storeRepository, StoreItemRepo storeItemRepository,
                         BasketItemRepo basketItemRepository) {
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
        this.storeItemRepository = storeItemRepository;
        this.basketItemRepository = basketItemRepository;
    }


    /**
     * Creates a new basket with associated items
     *
     * @param request the request containing basket and item details
     * @return the created BasketResponse
     * @throws NotFoundException if user, store, or store item is not found
     */
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


    /**
     * Retrieves a basket by its ID
     *
     * @param id the ID of the basket
     * @return the BasketResponse
     * @throws NotFoundException if the basket is not found
     */
    public BasketResponse getBasketById(Long id) {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Basket not found"));
        return convertToResponse(basket);
    }


    /**
     * Retrieves all baskets associated with a specific user
     *
     * @param userId the user's ID
     * @return list of BasketResponses
     * @throws NotFoundException if the user is not found
     */
    public List<BasketResponse> getBasketsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<Basket> baskets = basketRepository.findByUserId(userId);
        return baskets.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    /**
     * Deletes a basket by its ID.
     *
     * @param id the ID of the basket to delete
     * @throws NotFoundException if the basket is not found
     */
    @Transactional
    public void deleteBasket(Long id) {
        Basket basket = basketRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Basket not found"));
        basketRepository.delete(basket);
    }

    public BasketResponse convertToResponse(Basket basket) {
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


    /**
     * Finds a basket by user ID and store ID
     *
     * @param userId the user ID
     * @param storeId the store ID
     * @return an Optional containing the Basket if found
     */
    public Optional<Basket> findByUserIdAndStoreId(Long userId, Long storeId) {
        return basketRepository.findByUserIdAndStoreId(userId, storeId);
    }


    /**
     * Creates and persists a new Basket entity from the given request
     *
     * @param request the basket request
     * @return the created Basket entity
     * @throws NotFoundException if user, store, or store item is not found
     */
    public Basket createBasketEntity(BasketRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("User not found with id: " + request.userId()));

        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new NotFoundException("Store not found with id: " + request.storeId()));

        Basket basket = new Basket();
        basket.setUser(user);
        basket.setStore(store);
        basket.setItems(new ArrayList<>());

        // Save the basket first to generate ID
        Basket savedBasket = basketRepository.save(basket);

        // Process items
        for (BasketItemRequest itemRequest : request.items()) {
            StoreItem storeItem = storeItemRepository.findById(itemRequest.storeItemId())
                    .orElseThrow(() -> new NotFoundException("StoreItem not found with id: " + itemRequest.storeItemId()));

            BasketItem basketItem = new BasketItem();
            basketItem.setBasket(savedBasket);
            basketItem.setStoreItem(storeItem);
            basketItem.setQuantity(itemRequest.quantity());
            basketItem.setPriceAtAddition(storeItem.getTotalPrice());

            savedBasket.getItems().add(basketItem);
        }

        return basketRepository.save(savedBasket);
    }


    /**
     * Retrieves a Basket entity by its ID
     *
     * @param basketId the ID of the basket
     * @return the Basket entity
     * @throws NotFoundException if the basket is not found
     */
    public Basket getBasketEntityById(Long basketId) {
        return basketRepository.findById(basketId)
                .orElseThrow(() -> new NotFoundException("Basket not found with id: " + basketId));
    }



    /**
     * Optimizes user's baskets by relocating items to the baskets with the cheapest available store items.
     *
     * @param userId the user's ID
     * @return list of optimized BasketResponses
     * @throws NotFoundException if the user is not found
     */
    @Transactional
    public List<BasketResponse> optimizeBaskets(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<Basket> userBaskets = basketRepository.findByUserId(userId);

        // Collecting all the items from all the baskets
        List<BasketItem> allItems = userBaskets.stream()
                .flatMap(b -> b.getItems().stream())
                .collect(Collectors.toList());

        for (BasketItem item : allItems) {
            Item itemEntity = item.getStoreItem().getItem();
            List<StoreItem> allStoreItems = storeItemRepository.findByItemId(itemEntity.getId());

            if (allStoreItems.isEmpty()) continue;

            // Finding the cheapest StoreItem throughout all the stores
            StoreItem cheapestStoreItem = allStoreItems.stream()
                    .min(Comparator.comparing(StoreItem::getTotalPrice))
                    .orElseThrow();

            if (cheapestStoreItem.getStore().getId().equals(item.getBasket().getStore().getId())) {
                continue;
            }

            // Finding the basket or creating the basket for the store with the minimum price
            Long targetStoreId = cheapestStoreItem.getStore().getId();
            Basket targetBasket = basketRepository.findByUserIdAndStoreId(userId, targetStoreId)
                    .orElseGet(() -> {
                        Basket newBasket = new Basket();
                        newBasket.setUser(user);
                        newBasket.setStore(cheapestStoreItem.getStore());
                        return basketRepository.save(newBasket);
                    });

            // Moving or updating the item in the new basket
            Optional<BasketItem> existingItem = targetBasket.getItems().stream()
                    .filter(i -> i.getStoreItem().getId().equals(cheapestStoreItem.getId()))
                    .findFirst();

            if (existingItem.isPresent()) {
                // Updating the quantity if necessary
                BasketItem existing = existingItem.get();
                existing.setQuantity(existing.getQuantity() + item.getQuantity());
                existing.setPriceAtAddition(cheapestStoreItem.getTotalPrice());
                basketItemRepository.save(existing);
            } else {
                // If quantity = 0 => we create the new BasketItem
                BasketItem newItem = new BasketItem(cheapestStoreItem, item.getQuantity());
                newItem.setBasket(targetBasket);
                basketItemRepository.save(newItem);
                targetBasket.getItems().add(newItem);
            }

            // And we delete the BasketItem from the old basket
            Basket originalBasket = item.getBasket();
            originalBasket.getItems().remove(item);
            basketItemRepository.delete(item);
            basketRepository.save(originalBasket);
        }

        return basketRepository.findByUserId(userId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

}