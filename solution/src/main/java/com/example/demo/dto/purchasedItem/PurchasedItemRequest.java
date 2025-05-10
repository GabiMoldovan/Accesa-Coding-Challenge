package com.example.demo.dto.purchasedItem;

import com.example.demo.model.enums.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PurchasedItemRequest(

        @Schema(description = "The spending ID that the item is part of")
        @NotNull(message = "Spending ID cannot be null")
        Long spendingId,

        @Schema(description = "The name of the purchased item")
        @NotNull(message = "Item name cannot be null")
        String itemName,

        @Schema(description = "The price per unit at the time of purchase")
        @Min(value = 0, message = "Price per unit must be positive")
        float pricePerUnit,

        @Schema(description = "The number of units purchased")
        @Min(value = 0, message = "Units must be positive")
        float units,

        @Schema(description = "The unit type at the time of purchase")
        @NotNull(message = "Unit type cannot be null")
        UnitType unitType

) {
    public PurchasedItemRequest(Long spendingId, String itemName, float pricePerUnit, float units, UnitType unitType) {
        this.spendingId = spendingId;
        this.itemName = itemName;
        this.pricePerUnit = pricePerUnit;
        this.units = units;
        this.unitType = unitType;
    }
}
