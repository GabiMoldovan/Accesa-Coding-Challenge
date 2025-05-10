package com.example.demo.dto.priceAlert;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PriceAlertRequest(

        @Schema(description = "The ID of the user setting the price alert")
        @NotNull(message = "User ID cannot be null")
        Long userId,

        @Schema(description = "The ID of the product in the respective store")
        @NotNull(message = "Store item ID cannot be null")
        Long storeItemId,

        @Schema(description = "The target price at which the user wants to buy the product")
        @NotNull(message = "Target price cannot be null")
        @Positive(message = "Target price must be positive")
        float targetPrice
) {
    public PriceAlertRequest(Long userId, Long storeItemId, float targetPrice) {
        this.userId = userId;
        this.storeItemId = storeItemId;
        this.targetPrice = targetPrice;
    }
}
