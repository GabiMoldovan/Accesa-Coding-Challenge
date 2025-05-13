package com.example.demo.dto.storeItemBestValueResponse;

import com.example.demo.model.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;

public record StoreItemBestValueResponse(
        @Schema(description = "Store item ID")
        Long storeItemId,

        @Schema(description = "Store ID")
        Long storeId,

        @Schema(description = "Item ID")
        Long itemId,

        @Schema(description = "Preț total")
        float totalPrice,

        @Schema(description = "Număr de unități în pachet")
        float units,

        @Schema(description = "Valoare pe unitate (preț / unități)")
        float valuePerUnit,

        @Schema(description = "Monedă")
        Currency currency
) {}