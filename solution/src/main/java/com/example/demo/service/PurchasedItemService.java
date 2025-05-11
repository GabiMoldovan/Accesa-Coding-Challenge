package com.example.demo.service;

import com.example.demo.dto.purchasedItem.PurchasedItemRequest;
import com.example.demo.dto.purchasedItem.PurchasedItemResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.PurchasedItem;
import com.example.demo.model.Spending;
import com.example.demo.repository.PurchasedItemRepo;
import com.example.demo.repository.SpendingRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchasedItemService {
    private final PurchasedItemRepo purchasedItemRepository;
    private final SpendingRepo spendingRepository;

    public PurchasedItemService(PurchasedItemRepo purchasedItemRepository,
                                SpendingRepo spendingRepository) {
        this.purchasedItemRepository = purchasedItemRepository;
        this.spendingRepository = spendingRepository;
    }

    @Transactional
    public PurchasedItemResponse createPurchasedItem(PurchasedItemRequest request) {
        Spending spending = spendingRepository.findById(request.spendingId())
                .orElseThrow(() -> new NotFoundException("Spending not found"));

        PurchasedItem purchasedItem = new PurchasedItem();
        purchasedItem.setSpending(spending);
        purchasedItem.setItemName(request.itemName());
        purchasedItem.setPricePerUnit(request.pricePerUnit());
        purchasedItem.setUnits(request.units());
        purchasedItem.setUnitType(request.unitType());

        PurchasedItem savedItem = purchasedItemRepository.save(purchasedItem);
        return convertToResponse(savedItem);
    }

    public PurchasedItemResponse getPurchasedItemById(Long id) {
        PurchasedItem purchasedItem = purchasedItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PurchasedItem not found"));
        return convertToResponse(purchasedItem);
    }

    public List<PurchasedItemResponse> getAllBySpendingId(Long spendingId) {
        return purchasedItemRepository.findBySpendingId(spendingId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public PurchasedItemResponse updatePurchasedItem(Long id, PurchasedItemRequest request) {
        PurchasedItem purchasedItem = purchasedItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("PurchasedItem not found"));

        purchasedItem.setItemName(request.itemName());
        purchasedItem.setPricePerUnit(request.pricePerUnit());
        purchasedItem.setUnits(request.units());
        purchasedItem.setUnitType(request.unitType());

        return convertToResponse(purchasedItemRepository.save(purchasedItem));
    }

    @Transactional
    public void deletePurchasedItem(Long id) {
        purchasedItemRepository.deleteById(id);
    }

    private PurchasedItemResponse convertToResponse(PurchasedItem purchasedItem) {
        return new PurchasedItemResponse(
                purchasedItem.getId(),
                purchasedItem.getSpending().getId(),
                purchasedItem.getItemName(),
                purchasedItem.getPricePerUnit(),
                purchasedItem.getUnits(),
                purchasedItem.getUnitType()
        );
    }
}