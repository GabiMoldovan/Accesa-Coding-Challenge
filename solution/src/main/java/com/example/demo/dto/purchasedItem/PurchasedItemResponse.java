package com.example.demo.dto.purchasedItem;

import com.example.demo.model.enums.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;

public record PurchasedItemResponse(

        @Schema(description = "The ID of the purchased item")
        Long id,

        @Schema(description = "The ID of the corresponding spending")
        Long spendingId,

        @Schema(description = "The name of the item")
        String itemName,

        @Schema(description = "The price per unit at the time of purchase")
        float pricePerUnit,

        @Schema(description = "The number of units purchased")
        float units,

        @Schema(description = "The unit type at the time of purchase")
        UnitType unitType

) {
    public PurchasedItemResponse(Long id, Long spendingId, String itemName, float pricePerUnit, float units, UnitType unitType) {
        this.id = id;
        this.spendingId = spendingId;
        this.itemName = itemName;
        this.pricePerUnit = pricePerUnit;
        this.units = units;
        this.unitType = unitType;
    }
}
