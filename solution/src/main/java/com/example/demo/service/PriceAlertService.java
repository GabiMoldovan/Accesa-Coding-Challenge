package com.example.demo.service;

import com.example.demo.dto.priceAlert.PriceAlertRequest;
import com.example.demo.dto.priceAlert.PriceAlertResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.PriceAlert;
import com.example.demo.model.StoreItem;
import com.example.demo.model.User;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

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
}