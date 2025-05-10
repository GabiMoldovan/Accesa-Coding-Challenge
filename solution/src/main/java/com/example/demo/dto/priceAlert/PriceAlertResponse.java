package com.example.demo.dto.priceAlert;

import io.swagger.v3.oas.annotations.media.Schema;

public record PriceAlertResponse(

        @Schema(description = "The price alert ID")
        Long priceAlertId,

        @Schema(description = "The ID of the user who set the alert")
        Long userId,

        @Schema(description = "The ID of the product in the respective store")
        Long storeItemId,

        @Schema(description = "The target price for the respective product")
        float targetPrice
) {
    public PriceAlertResponse(Long priceAlertId, Long userId, Long storeItemId, float targetPrice) {
        this.priceAlertId = priceAlertId;
        this.userId = userId;
        this.storeItemId = storeItemId;
        this.targetPrice = targetPrice;
    }
}
