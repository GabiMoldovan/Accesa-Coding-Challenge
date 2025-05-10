package com.example.demo.dto.store;

import com.example.demo.dto.itemDiscount.ItemDiscountRequest;
import com.example.demo.dto.priceHistory.PriceHistoryRequest;
import com.example.demo.dto.storeItem.StoreItemRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record StoreRequest(

        @Schema(description = "The name of the company that owns the store")
        @NotNull(message = "Company name cannot be null")
        String companyName,

        @Schema(description = "The list of products available in the store")
        @NotNull(message = "Store items cannot be null")
        List<StoreItemRequest> storeItems,

        @Schema(description = "The list of discounts applied to the products")
        @NotNull(message = "Discounts cannot be null")
        List<ItemDiscountRequest> discounts,

        @Schema(description = "The list of product price history entries")
        @NotNull(message = "Price history entries cannot be null")
        List<PriceHistoryRequest> priceHistory
) {
    public StoreRequest(String companyName, List<StoreItemRequest> storeItems, List<ItemDiscountRequest> discounts, List<PriceHistoryRequest> priceHistory) {
        this.companyName = companyName;
        this.storeItems = storeItems;
        this.discounts = discounts;
        this.priceHistory = priceHistory;
    }
}
