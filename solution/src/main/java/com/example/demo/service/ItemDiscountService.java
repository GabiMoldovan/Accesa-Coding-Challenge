package com.example.demo.service;

import com.example.demo.dto.itemDiscount.ItemDiscountRequest;
import com.example.demo.dto.itemDiscount.ItemDiscountResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.ItemDiscount;
import com.example.demo.model.Store;
import com.example.demo.model.StoreItem;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemDiscountService {
    private final ItemDiscountRepo discountRepository;
    private final StoreItemRepo storeItemRepository;
    private final StoreRepo storeRepository;

    public ItemDiscountService(ItemDiscountRepo discountRepository,
                               StoreItemRepo storeItemRepository, StoreRepo storeRepository) {
        this.discountRepository = discountRepository;
        this.storeItemRepository = storeItemRepository;
        this.storeRepository = storeRepository;
    }

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

    public ItemDiscountResponse deleteDiscount(Long id) {
        ItemDiscount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found"));

        discountRepository.delete(discount);
        return convertToResponse(discount);
    }


    private ItemDiscountResponse convertToResponse(ItemDiscount discount) {
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

    public List<ItemDiscountResponse> getActiveDiscounts(LocalDateTime date) {
        return discountRepository
                .findByStartDateLessThanEqualAndEndDateGreaterThanEqual(date, date)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }


    public List<ItemDiscountResponse> getTopDiscounts(int limit) {
        return discountRepository
                .findAllByOrderByDiscountPercentageDesc()
                .stream()
                .limit(limit)
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ItemDiscountResponse> getDiscountsForItem(Long itemId) {
        List<ItemDiscount> discounts = discountRepository.findAllByStoreItem_Item_Id(itemId);
        return discounts.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

}