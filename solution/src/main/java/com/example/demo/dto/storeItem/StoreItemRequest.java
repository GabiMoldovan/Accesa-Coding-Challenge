package com.example.demo.dto.storeItem;

import com.example.demo.model.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record StoreItemRequest(

        @Schema(description = "The store ID where the product is located")
        @NotNull(message = "Store ID cannot be null")
        Long storeId,

        @Schema(description = "The product ID")
        @NotNull(message = "Item ID cannot be null")
        Long itemId,

        @Schema(description = "the total price of a product")
        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        float totalPrice,


        @Schema(description = "The number of units contained in the package")
        @NotNull(message = "Units cannot be null")
        @Positive(message = "Units must be positive")
        float units,

        @Schema(description = "The currency in which the product is sold")
        @NotNull(message = "Currency cannot be null")
        Currency currency
) {
    public StoreItemRequest(Long storeId, Long itemId,float totalPrice, float units, Currency currency) {
        this.storeId = storeId;
        this.itemId = itemId;
        this.totalPrice = totalPrice;
        this.units = units;
        this.currency = currency;
    }
}
