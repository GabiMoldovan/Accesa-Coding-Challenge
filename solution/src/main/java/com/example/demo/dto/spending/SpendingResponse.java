package com.example.demo.dto.spending;

import com.example.demo.dto.purchasedItem.PurchasedItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

public record SpendingResponse(

        @Schema(description = "The spending ID")
        Long spendingId,

        @Schema(description = "The ID of the user who made the spending")
        Long userId,

        @Schema(description = "The ID of the store where the purchase was made")
        Long storeId,

        @Schema(description = "The list of purchased items")
        List<PurchasedItemResponse> purchasedItems,

        @Schema(description = "The total price of the purchase")
        float totalPrice,

        @Schema(description = "The date when the purchase was made")
        LocalDateTime purchaseDate
) {
    public SpendingResponse(Long spendingId, Long userId, Long storeId, List<PurchasedItemResponse> purchasedItems, float totalPrice, LocalDateTime purchaseDate) {
        this.spendingId = spendingId;
        this.userId = userId;
        this.storeId = storeId;
        this.purchasedItems = purchasedItems;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }
}
