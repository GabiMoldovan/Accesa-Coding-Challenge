package com.example.demo.dto.itemPatchRequest;

import com.example.demo.model.enums.Category;
import com.example.demo.model.enums.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

public record ItemPatchRequest(
        @Schema(description = "Updated name of the item")
        @Size(min = 1, max = 128)
        String itemName,

        @Schema(description = "Updated category of the item")
        Category category,

        @Schema(description = "Updated brand of the item")
        @Size(min = 1, max = 64)
        String brand,

        @Schema(description = "Updated unit type of the item")
        UnitType unitType
) {}