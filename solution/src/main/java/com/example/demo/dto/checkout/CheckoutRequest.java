package com.example.demo.dto.checkout;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CheckoutRequest(
        @Schema(description = "The user ID that owns the basket")
        @NotNull(message = "User ID cannot be null")
        Long userId
) {
    public CheckoutRequest(Long userId) {
        this.userId = userId;
    }
}
