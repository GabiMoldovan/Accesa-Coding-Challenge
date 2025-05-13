package com.example.demo.dto.basket;

import com.example.demo.dto.basketItem.BasketItemRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BasketRequest(

        @Schema(description = "The user ID that owns the basket")
        @NotNull(message = "User ID cannot be null")
        Long userId,

        @Schema(description = "The store ID to which the basket belongs to")
        @NotNull(message = "Store ID cannot be null")
        Long storeId,

        @Schema(description = "The list of items that will be in the basket")
        @NotNull(message = "Items cannot be null")
        @Size(min = 1, message = "At least one item must be provided")
        List<BasketItemRequest> items
) {
    public BasketRequest(Long userId, Long storeId, List<BasketItemRequest> items) {
        this.userId = userId;
        this.storeId = storeId;
        this.items = items;
    }
}