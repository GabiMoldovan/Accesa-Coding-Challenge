package com.example.demo.dto.spending;

import com.example.demo.dto.purchasedItem.PurchasedItemRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public record SpendingRequest(

        @Schema(description = "The ID of the user who made the purchase")
        @NotNull(message = "User ID cannot be null")
        Long userId,

        @Schema(description = "The ID of the store where the purchase was made")
        @NotNull(message = "Store ID cannot be null")
        Long storeId,

        @Schema(description = "The list of purchased items")
        @NotNull(message = "Purchased items cannot be null")
        List<PurchasedItemRequest> purchasedItems,

        @Schema(description = "The total price of the purchase")
        @NotNull(message = "Total price cannot be null")
        @Positive(message = "Total price must be positive")
        float totalPrice,

        @Schema(description = "The date the purchase was made")
        @NotNull(message = "Purchase date cannot be null")
        LocalDateTime purchaseDate
) {
    public SpendingRequest(Long userId, Long storeId, List<PurchasedItemRequest> purchasedItems, float totalPrice, LocalDateTime purchaseDate) {
        this.userId = userId;
        this.storeId = storeId;
        this.purchasedItems = purchasedItems;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }
}
