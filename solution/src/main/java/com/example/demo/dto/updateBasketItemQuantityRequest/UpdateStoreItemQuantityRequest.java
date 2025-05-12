package com.example.demo.dto.updateBasketItemQuantityRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateStoreItemQuantityRequest(
        @NotNull(message = "Quantity cannot be null")
        @Min(value = 0, message = "Quantity must be positive")
        @Schema(description = "The new quantity to be set", example = "2.5")
        float quantity
) {}