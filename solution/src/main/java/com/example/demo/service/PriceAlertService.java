package com.example.demo.service;

import com.example.demo.dto.priceAlert.PriceAlertRequest;
import com.example.demo.dto.priceAlert.PriceAlertResponse;
import com.example.demo.dto.triggeredPriceAlertResponse.TriggeredPriceAlertResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Item;
import com.example.demo.model.PriceAlert;
import com.example.demo.model.StoreItem;
import com.example.demo.model.User;
import com.example.demo.repository.*;
import com.example.demo.utils.mapper.StoreItemMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PriceAlertService {
    private final PriceAlertRepo alertRepository;
    private final UserRepo userRepository;
    private final StoreItemRepo storeItemRepository;

    public PriceAlertService(PriceAlertRepo alertRepository, UserRepo userRepository,
                             StoreItemRepo storeItemRepository) {
        this.alertRepository = alertRepository;
        this.userRepository = userRepository;
        this.storeItemRepository = storeItemRepository;
    }

    public PriceAlertResponse createAlert(PriceAlertRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));
        StoreItem storeItem = storeItemRepository.findById(request.storeItemId())
                .orElseThrow(() -> new NotFoundException("StoreItem not found"));

        PriceAlert alert = new PriceAlert();
        alert.setUser(user);
        alert.setStoreItem(storeItem);
        alert.setTargetPrice(request.targetPrice());

        PriceAlert savedAlert = alertRepository.save(alert);
        return convertToResponse(savedAlert);
    }

    private PriceAlertResponse convertToResponse(PriceAlert alert) {
        return new PriceAlertResponse(
                alert.getId(),
                alert.getUser().getId(),
                alert.getStoreItem().getId(),
                alert.getTargetPrice()
        );
    }

    @Transactional
    public void checkAllAlerts() {
        List<PriceAlert> alerts = alertRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        alerts.forEach(alert -> {
            StoreItem item = storeItemRepository.findById(alert.getStoreItem().getId())
                    .orElseThrow(() -> new NotFoundException("StoreItem not found"));

            float currentPrice = item.getTotalPrice();
            if(currentPrice <= alert.getTargetPrice()) {
                //System.out.println("Price alert triggered for user: " + alert.getUser().getEmail());
                alertRepository.delete(alert);
            }
        });
    }

    public PriceAlertResponse updateAlert(Long id, float newPrice) {
        PriceAlert alert = alertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Price alert not found"));

        alert.setTargetPrice(newPrice);
        PriceAlert updated = alertRepository.save(alert);
        return convertToResponse(updated);
    }

    public PriceAlertResponse deleteAlert(Long id) {
        PriceAlert alert = alertRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Price alert not found"));

        alertRepository.delete(alert);
        return convertToResponse(alert);
    }


    public List<PriceAlertResponse> getAlertsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return alertRepository.findAll().stream()
                .filter(alert -> alert.getUser().getId().equals(userId))
                .map(this::convertToResponse)
                .toList();
    }

    public List<TriggeredPriceAlertResponse> getTriggeredAlerts(Long userId) {
        List<PriceAlert> alerts = alertRepository.findByUserId(userId);

        return alerts.stream()
                .map(alert -> {
                    // Gets the store item from the alert
                    StoreItem alertStoreItem = alert.getStoreItem();

                    // Gets the item
                    Item targetItem = alertStoreItem.getItem();

                    // Searches for all store items of that item
                    List<StoreItem> allStoreItems = storeItemRepository.findByItemId(targetItem.getId());

                    if (allStoreItems.isEmpty()) return null;

                    // Gets the store item with the lowest price
                    StoreItem cheapestStoreItem = allStoreItems.stream()
                            .min(Comparator.comparing(StoreItem::getTotalPrice))
                            .orElse(null);

                    if (cheapestStoreItem == null) return null;

                    // Verifies if the price of the store item is > target price of the alert
                    if (cheapestStoreItem.getTotalPrice() > alert.getTargetPrice()) {
                        return null;
                    }

                    return new TriggeredPriceAlertResponse(
                            StoreItemMapper.entityToDto(cheapestStoreItem),
                            alert.getTargetPrice()
                    );
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}