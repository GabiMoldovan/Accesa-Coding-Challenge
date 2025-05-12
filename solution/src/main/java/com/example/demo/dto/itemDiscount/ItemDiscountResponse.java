package com.example.demo.dto.itemDiscount;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record ItemDiscountResponse(

        @Schema(description = "The discount ID")
        Long itemDiscountId,

        @Schema(description = "The ID of the product in the respective store")
        Long storeItemId,

        @Schema(description = "The ID of the store where the discount is applied")
        Long storeId,

        @Schema(description = "The old price of the item")
        float oldPrice,

        @Schema(description = "The percentage of the applied discount")
        float discountPercentage,

        @Schema(description = "The start date of the discount")
        LocalDateTime startDate,

        @Schema(description = "The end date of the discount")
        LocalDateTime endDate,

        @Schema(description = "The ID of the item")
                Long itemId
) {
    public ItemDiscountResponse(Long itemDiscountId, Long storeItemId, Long storeId,
                                float oldPrice, float discountPercentage, LocalDateTime startDate,
                                LocalDateTime endDate, Long itemId) {
        this.itemDiscountId = itemDiscountId;
        this.storeItemId = storeItemId;
        this.storeId = storeId;
        this.oldPrice = oldPrice;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.itemId = itemId;
    }
}
