package com.example.demo.dto.basketItem;

import io.swagger.v3.oas.annotations.media.Schema;

public record BasketItemResponse(

        @Schema(description = "Cart item ID")
        Long id,

        @Schema(description = "The ID of the cart to which the item belongs")
        Long basketId,

        @Schema(description = "The product ID from stock")
        Long storeItemId,

        @Schema(description = "The quantity of the product added")
        float quantity,

        @Schema(description = "The price per unit at the time of addition")
        float priceAtAddition

) {
    public BasketItemResponse(Long id, Long basketId, Long storeItemId, float quantity, float priceAtAddition) {
        this.id = id;
        this.basketId = basketId;
        this.storeItemId = storeItemId;
        this.quantity = quantity;
        this.priceAtAddition = priceAtAddition;
    }
}