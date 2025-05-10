package com.example.demo.dto.user;

import com.example.demo.dto.basket.BasketResponse;
import com.example.demo.dto.priceAlert.PriceAlertResponse;
import com.example.demo.dto.spending.SpendingResponse;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UserResponse(

        @Schema(description = "The user's ID")
        Long userId,

        @Schema(description = "The user's first name")
        String firstName,

        @Schema(description = "The user's last name")
        String lastName,

        @Schema(description = "The user's email address")
        String email,

        @Schema(description = "The user's list of spendings")
        List<SpendingResponse> spending,

        @Schema(description = "The user's list of baskets")
        List<BasketResponse> baskets,

        @Schema(description = "The user's list of price alerts")
        List<PriceAlertResponse> priceAlerts
) {
    public UserResponse(Long userId, String firstName, String lastName, String email, List<SpendingResponse> spending, List<BasketResponse> baskets, List<PriceAlertResponse> priceAlerts) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.spending = spending;
        this.baskets = baskets;
        this.priceAlerts = priceAlerts;
    }
}
