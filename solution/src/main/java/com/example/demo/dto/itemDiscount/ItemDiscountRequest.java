package com.example.demo.dto.itemDiscount;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record ItemDiscountRequest(

        @Schema(description = "The ID of the product in the respective store")
        @NotNull(message = "Store item ID cannot be null")
        Long storeItemId,

        @Schema(description = "The ID of the store where the discount is applied")
        @NotNull(message = "Store ID cannot be null")
        Long storeId,

        @Schema(description = "The old price of the item")
        @NotNull(message = "Old price cannot be null")
        @Positive(message = "Old price must be positive")
        float oldPrice,

        @Schema(description = "The percentage of the applied discount")
        @NotNull(message = "Discount percentage cannot be null")
        @Positive(message = "Discount percentage must be positive")
        float discountPercentage,

        @Schema(description = "The start date of the discount")
        @NotNull(message = "Start date cannot be null")
        LocalDateTime startDate,

        @Schema(description = "The end date of the discount")
        @NotNull(message = "End date cannot be null")
        LocalDateTime endDate
) {
    public ItemDiscountRequest(Long storeItemId, Long storeId, float oldPrice, float discountPercentage, LocalDateTime startDate, LocalDateTime endDate) {
        this.storeItemId = storeItemId;
        this.storeId = storeId;
        this.oldPrice = oldPrice;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
