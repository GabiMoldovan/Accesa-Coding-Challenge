package com.example.demo.dto.triggeredPriceAlertResponse;

import com.example.demo.dto.storeItem.StoreItemResponse;
import io.swagger.v3.oas.annotations.media.Schema;

public record TriggeredPriceAlertResponse(
        @Schema(description = "The cheapest StoreItem for an Item")
        StoreItemResponse storeItem,
        @Schema(description = "The target price set by the user")
        float targetPrice
) {}