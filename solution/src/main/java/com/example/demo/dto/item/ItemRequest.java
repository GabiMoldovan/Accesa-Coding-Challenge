package com.example.demo.dto.item;


import com.example.demo.model.enums.Category;
import com.example.demo.model.enums.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ItemRequest(
        @Schema(description = "The item name")
        @NotNull(message = "Item name cannot be null")
        @Size(min = 1, max = 128, message = "Item name must be between 1 and 128 characters")
        String itemName,

        @Schema(description = "The item category")
        @NotNull(message = "Category cannot be null")
        Category category,

        @Schema(description = "The item brand")
        @NotNull(message = "Brand cannot be null")
        @Size(min = 1, max = 64, message = "Brand name must be between 1 and 64 characters")
        String brand,

        @Schema(description = "The item unit type")
        @NotNull(message = "Unit type cannot be null")
        UnitType unitType
) {
    public ItemRequest(String itemName, Category category, String brand, UnitType unitType) {
        this.itemName = itemName;
        this.category = category;
        this.brand = brand;
        this.unitType = unitType;
    }
}
