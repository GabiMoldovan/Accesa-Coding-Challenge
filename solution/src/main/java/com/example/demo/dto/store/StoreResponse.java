package com.example.demo.dto.store;

import com.example.demo.dto.itemDiscount.ItemDiscountResponse;
import com.example.demo.dto.priceHistory.PriceHistoryResponse;
import com.example.demo.dto.storeItem.StoreItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record StoreResponse(

        @Schema(description = "The store ID")
        Long storeId,

        @Schema(description = "The name of the company that owns the store")
        String companyName,

        @Schema(description = "The list of products available in the store")
        List<StoreItemResponse> storeItems,

        @Schema(description = "The list of discounts applied to the products")
        List<ItemDiscountResponse> discounts,

        @Schema(description = "The list of product price history entries")
        List<PriceHistoryResponse> priceHistory
) {
    public StoreResponse(Long storeId, String companyName, List<StoreItemResponse> storeItems, List<ItemDiscountResponse> discounts, List<PriceHistoryResponse> priceHistory) {
        this.storeId = storeId;
        this.companyName = companyName;
        this.storeItems = storeItems;
        this.discounts = discounts;
        this.priceHistory = priceHistory;
    }
}
