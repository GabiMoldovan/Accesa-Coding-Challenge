package com.example.demo.dto.storeItem;

import com.example.demo.model.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;

public record StoreItemResponse(

        @Schema(description = "The store item ID")
        Long storeItemId,

        @Schema(description = "The store ID where the product is located")
        Long storeId,

        @Schema(description = "The product ID")
        Long itemId,

        @Schema(description = "The price per unit of the product")
        float pricePerUnit,

        @Schema(description = "The number of units contained in the package")
        float units,

        @Schema(description = "The currency in which the product is sold")
        Currency currency,

        @Schema(description = "The total price of the product")
        float totalPrice
) {
    public StoreItemResponse(Long storeItemId, Long storeId, Long itemId, float pricePerUnit, float units, Currency currency, float totalPrice) {
        this.storeItemId = storeItemId;
        this.storeId = storeId;
        this.itemId = itemId;
        this.pricePerUnit = pricePerUnit;
        this.units = units;
        this.currency = currency;
        this.totalPrice = totalPrice;
    }
}
