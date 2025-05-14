package com.example.demo.service;

import com.example.demo.dto.spending.SpendingRequest;
import com.example.demo.dto.spending.SpendingResponse;
import com.example.demo.dto.purchasedItem.PurchasedItemResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpendingService {
    private final SpendingRepo spendingRepository;
    private final UserRepo userRepository;
    private final StoreRepo storeRepository;

    public SpendingService(SpendingRepo spendingRepository, UserRepo userRepository,
                           StoreRepo storeRepository) {
        this.spendingRepository = spendingRepository;
        this.userRepository = userRepository;
        this.storeRepository = storeRepository;
    }


    /**
     * Creates a new spending entry along with associated purchased items
     *
     * @param request the spending request containing user ID, store ID, total price, purchase date, and purchased items
     * @return the created spending as a response DTO
     * @throws NotFoundException if the user or store associated with the request is not found
     */
    @Transactional
    public SpendingResponse createSpending(SpendingRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        Store store = storeRepository.findById(request.storeId())
                .orElseThrow(() -> new NotFoundException("Store not found"));

        Spending spending = new Spending();
        spending.setUser(user);
        spending.setStore(store);
        spending.setTotalPrice(request.totalPrice());
        spending.setPurchaseDate(request.purchaseDate());

        List<PurchasedItem> purchasedItems = request.purchasedItems().stream()
                .map(itemReq -> {
                    PurchasedItem item = new PurchasedItem();
                    item.setSpending(spending);
                    item.setItemName(itemReq.itemName());
                    item.setPricePerUnit(itemReq.pricePerUnit());
                    item.setUnits(itemReq.units());
                    item.setUnitType(itemReq.unitType());
                    return item;
                })
                .collect(Collectors.toList());

        spending.setPurchasedItems(purchasedItems);
        Spending savedSpending = spendingRepository.save(spending);
        return convertToResponse(savedSpending);
    }

    private SpendingResponse convertToResponse(Spending spending) {
        List<PurchasedItemResponse> items = spending.getPurchasedItems().stream()
                .map(this::convertItemToResponse)
                .collect(Collectors.toList());

        return new SpendingResponse(
                spending.getId(),
                spending.getUser().getId(),
                spending.getStore().getId(),
                items,
                spending.getTotalPrice(),
                spending.getPurchaseDate()
        );
    }

    private PurchasedItemResponse convertItemToResponse(PurchasedItem item) {
        return new PurchasedItemResponse(
                item.getId(),
                item.getSpending().getId(),
                item.getItemName(),
                item.getPricePerUnit(),
                item.getUnits(),
                item.getUnitType()
        );
    }
}