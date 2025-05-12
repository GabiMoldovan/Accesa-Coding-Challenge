package com.example.demo.dto.priceAlertPatchRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PriceAlertPatchRequest(

        @Schema(description = "The new target price")
        @NotNull(message = "Target price cannot be null")
        @Positive(message = "Target price must be positive")
        float targetPrice
) {}
