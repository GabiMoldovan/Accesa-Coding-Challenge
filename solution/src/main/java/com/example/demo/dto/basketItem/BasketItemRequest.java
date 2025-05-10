package com.example.demo.dto.basketItem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BasketItemRequest(

        @Schema(description = "The ID of the cart to which the product is added")
        @NotNull(message = "Basket ID cannot be null")
        Long basketId,

        @Schema(description = "Product ID from stock\n")
        @NotNull(message = "StoreItem ID cannot be null")
        Long storeItemId,

        @Schema(description = "Quantity of the product added to the cart\n")
        @Min(value = 0, message = "Quantity must be positive")
        float quantity

) {
    public BasketItemRequest(Long basketId, Long storeItemId, float quantity) {
        this.basketId = basketId;
        this.storeItemId = storeItemId;
        this.quantity = quantity;
    }
}