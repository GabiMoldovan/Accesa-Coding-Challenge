package com.example.demo.service;

import com.example.demo.dto.itemDiscount.ItemDiscountRequest;
import com.example.demo.dto.itemDiscount.ItemDiscountResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.BasketItem;
import com.example.demo.model.ItemDiscount;
import com.example.demo.model.Store;
import com.example.demo.model.StoreItem;
import com.example.demo.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemDiscountService {
    private final ItemDiscountRepo discountRepository;
    private final StoreItemRepo storeItemRepository;
    private final StoreRepo storeRepository;
    private final PriceHistoryService priceHistoryService;
    private final BasketItemRepo basketItemRepository;

    public ItemDiscountService(ItemDiscountRepo discountRepository, StoreItemRepo storeItemRepository,
                               StoreRepo storeRepository, PriceHistoryService priceHistoryService,
                               BasketItemRepo basketItemRepository) {
        this.discountRepository = discountRepository;
        this.storeItemRepository = storeItemRepository;
        this.storeRepository = storeRepository;
        this.priceHistoryService = priceHistoryService;
        this.basketItemRepository = basketItemRepository;
    }


    /**
     * Creates a new discount for a store item using the provided request details
     *
     * @param request the discount request containing store item ID, store ID, old price, discount percentage, start and end dates
     * @return the created discount as a response DTO
     * @throws NotFoundException if the store item or store is not found
     */
    public ItemDiscountResponse createDiscount(ItemDiscountRequest request) {
        StoreItem storeItem = storeItemRepository.findById(request.storeItemId())
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new NotFoundException("Store not found"));

        ItemDiscount discount = new ItemDiscount();
        discount.setStoreItem(storeItem);
        discount.setStore(store);
        discount.setOldPrice(request.oldPrice());
        discount.setDiscountPercentage(request.discountPercentage());
        discount.setStartDate(request.startDate());
        discount.setEndDate(request.endDate());

        ItemDiscount savedDiscount = discountRepository.save(discount);
        return convertToResponse(savedDiscount);
    }


    /**
     * Updates an existing discount with new details
     *
     * @param id the ID of the discount to update
     * @param request the updated discount information
     * @return the updated discount as a response DTO
     * @throws NotFoundException if the discount, store item, or store is not found
     */
    public ItemDiscountResponse updateDiscount(Long id, ItemDiscountRequest request) {
        ItemDiscount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found"));

        StoreItem storeItem = storeItemRepository.findById(request.storeItemId())
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));

        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new NotFoundException("Store not found"));

        discount.setStoreItem(storeItem);
        discount.setStore(store);
        discount.setOldPrice(request.oldPrice());
        discount.setDiscountPercentage(request.discountPercentage());
        discount.setStartDate(request.startDate());
        discount.setEndDate(request.endDate());

        return convertToResponse(discountRepository.save(discount));
    }


    /**
     * Deletes a discount by its ID
     *
     * @param id the ID of the discount to delete
     * @return the deleted discount as a response DTO
     * @throws NotFoundException if the discount is not found
     */
    public ItemDiscountResponse deleteDiscount(Long id) {
        ItemDiscount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found"));

        discountRepository.delete(discount);
        return convertToResponse(discount);
    }




    private ItemDiscountResponse convertToResponse(ItemDiscount discount) {
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


    /**
     * Retrieves a list of discounts active at the specified date and time
     *
     * @param date the timestamp to check for active discounts
     * @return a list of active discounts as response DTOs
     */
    public List<ItemDiscountResponse> getActiveDiscounts(LocalDateTime date) {
        return discountRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(date, date)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a list of active discounts with unique items at the specified date and time
     *
     * @param date the timestamp to check for active discounts
     * @return a list of active discounts with unique items as response DTOs
     */
    public List<ItemDiscountResponse> getActiveDiscountsWithUniqueItems(LocalDateTime date) {
        return discountRepository.findActiveDiscountsWithItems(date, date)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a list of top discounts sorted by highest discount percentage
     *
     * @param limit the maximum number of discounts to return
     * @return a list of top discounts as response DTOs
     */
    public List<ItemDiscountResponse> getTopDiscounts(int limit) {
        return discountRepository
                .findAllByOrderByDiscountPercentageDesc()
                .stream()
                .limit(limit)
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    /**
     * Retrieves all discounts associated with a specific item
     *
     * @param itemId the ID of the item
     * @return a list of discounts for the specified item as response DTOs
     */
    public List<ItemDiscountResponse> getDiscountsForItem(Long itemId) {
        List<ItemDiscount> discounts = discountRepository.findAllByStoreItem_Item_Id(itemId);
        return discounts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    /**
     * Scheduled task that runs every minute to apply new discounts and remove expired ones
     *
     * Applies active discounts and reverts prices for discounts that have ended
     */
    @Scheduled(cron = "0 * * * * *") // This makes the metod run every minute
    @Transactional
    public void handleDiscountsAutomatically() {
        LocalDateTime now = LocalDateTime.now();

        // apply active discounts
        applyActiveDiscounts(now);

        // eliminate the expired discounts
        expireEndedDiscounts(now);
    }


    private void applyActiveDiscounts(LocalDateTime now) {
        List<ItemDiscount> activeDiscounts = discountRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(now, now);

        activeDiscounts.forEach(discount -> {
            StoreItem storeItem = discount.getStoreItem();
            float discountedPrice = calculateDiscountedPrice(discount);

            if (storeItem.getTotalPrice() != discountedPrice) {
                // Update store item price
                storeItem.setTotalPrice(discountedPrice);
                storeItemRepository.save(storeItem);

                // Update all basket items with this store item
                List<BasketItem> basketItems = basketItemRepository.findByStoreItem(storeItem);
                basketItems.forEach(basketItem -> {
                    basketItem.setPriceAtAddition(discountedPrice);
                    basketItemRepository.save(basketItem);
                });

                priceHistoryService.recordPriceChange(
                        storeItem.getItem().getId(),
                        storeItem.getStore().getId(),
                        discountedPrice
                );
            }
        });
    }

    private void expireEndedDiscounts(LocalDateTime now) {
        List<ItemDiscount> expiredDiscounts = discountRepository.findByEndDateLessThan(now);

        expiredDiscounts.forEach(discount -> {
            StoreItem storeItem = discount.getStoreItem();
            float originalPrice = discount.getOldPrice();

            if (storeItem.getTotalPrice() == calculateDiscountedPrice(discount)) {
                // Revert store item price
                storeItem.setTotalPrice(originalPrice);
                storeItemRepository.save(storeItem);

                // Update all basket items with this store item
                List<BasketItem> basketItems = basketItemRepository.findByStoreItem(storeItem);
                basketItems.forEach(basketItem -> {
                    basketItem.setPriceAtAddition(originalPrice);
                    basketItemRepository.save(basketItem);
                });

                priceHistoryService.recordPriceChange(
                        storeItem.getItem().getId(),
                        storeItem.getStore().getId(),
                        originalPrice
                );
            }
        });
    }

    private float calculateDiscountedPrice(ItemDiscount discount) {
        return discount.getOldPrice() * (1 - discount.getDiscountPercentage() / 100);
    }


    /**
     * Retrieves discounts that were active within the last given number of hours
     *
     * @param hours the number of past hours to check
     * @return a list of discounts that were active in the specified time window as response DTOs
     */
    public List<ItemDiscountResponse> getDiscountsActiveInLastGivenHours(int hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime cutoffStart = now.minusHours(hours);
        return discountRepository.findActiveInGivenRange(cutoffStart, now)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

}