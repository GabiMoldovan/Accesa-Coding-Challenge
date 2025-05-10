package com.example.demo.dto.item;

import com.example.demo.model.enums.Category;
import com.example.demo.model.enums.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;

public record ItemResponse(

        @Schema(description = "The item ID")
        Long itemId,

        @Schema(description = "The item name")
        String itemName,

        @Schema(description = "The item category")
        Category category,

        @Schema(description = "The item brand")
        String brand,

        @Schema(description = "The item unit type")
        UnitType unitType
) {
    public ItemResponse(Long itemId, String itemName, Category category, String brand, UnitType unitType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.brand = brand;
        this.unitType = unitType;
    }
}