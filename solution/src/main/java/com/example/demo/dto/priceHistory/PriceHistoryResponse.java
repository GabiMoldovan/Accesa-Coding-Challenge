package com.example.demo.dto.priceHistory;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PriceHistoryResponse(

        @Schema(description = "The price history ID")
        Long priceHistoryId,

        @Schema(description = "The ID of the product in the respective store")
        Long storeItemId,

        @Schema(description = "The ID of the store where the price was changed")
        Long storeId,

        @Schema(description = "The date when the price was changed")
        LocalDateTime date,

        @Schema(description = "The new price value")
        float price,

        @Schema(description = "The category of the product")
        String category,

        @Schema(description = "The brand of the product")
        String brand
) {
    public PriceHistoryResponse(Long priceHistoryId, Long storeItemId, Long storeId, LocalDateTime date, float price,
                                String category, String brand) {
        this.priceHistoryId = priceHistoryId;
        this.storeItemId = storeItemId;
        this.storeId = storeId;
        this.date = date;
        this.price = price;
        this.category = category;
        this.brand = brand;
    }
}
