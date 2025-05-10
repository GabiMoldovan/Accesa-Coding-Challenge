package com.example.demo.dto.basket;

import com.example.demo.dto.basketItem.BasketItemResponse;
import com.example.demo.model.BasketItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record BasketResponse(

        @Schema(description = "The basket ID")
        Long basketId,

        @Schema(description = "The ID of the user that owns the basket")
        Long userId,

        @Schema(description = "The ID of the store to which the basket belongs to")
        Long storeId,

        @Schema(description = "The list of items in the basket")
        List<BasketItemResponse> items

) {
    public BasketResponse(Long basketId, Long userId, Long storeId, List<BasketItemResponse> items) {
        this.basketId = basketId;
        this.userId = userId;
        this.storeId = storeId;
        this.items = items;
    }
}