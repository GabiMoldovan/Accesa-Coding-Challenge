package com.example.demo.dto.priceHistory;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PriceHistoryRequest(

        @Schema(description = "The ID of the product in the respective store")
        @NotNull(message = "Store item ID cannot be null")
        Long storeItemId,

        @Schema(description = "The ID of the store")
        @NotNull(message = "Store ID cannot be null")
        Long storeId,

        @Schema(description = "The date when the price was modified")
        @NotNull(message = "Date cannot be null")
        LocalDateTime date,

        @Schema(description = "The new price value")
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        float price
) {
    public PriceHistoryRequest(Long storeItemId, Long storeId, LocalDateTime date, float price) {
        this.storeItemId = storeItemId;
        this.storeId = storeId;
        this.date = date;
        this.price = price;
    }
}
