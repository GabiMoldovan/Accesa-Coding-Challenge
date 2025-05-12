package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.basket.BasketResponse;
import com.example.demo.dto.purchasedItem.PurchasedItemRequest;
import com.example.demo.dto.spending.SpendingRequest;
import com.example.demo.dto.spending.SpendingResponse;
import com.example.demo.model.Basket;
import com.example.demo.model.BasketItem;
import com.example.demo.service.BasketService;
import com.example.demo.service.SpendingService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/baskets")
@Validated
public class BasketController {
    private final BasketService basketService;
    private final SpendingService spendingService;
    private final UserService userService;

    @Autowired
    public BasketController(BasketService basketService,
                            SpendingService spendingService,
                            UserService userService) {
        this.basketService = basketService;
        this.spendingService = spendingService;
        this.userService = userService;
    }


    @Operation(summary = "Get all baskets of a user", description = "Returns all baskets belonging to the specified user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of baskets",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BasketResponse.class, type = "array"))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BasketResponse>> getUserBaskets(@PathVariable Long userId) {
        List<BasketResponse> baskets = basketService.getBasketsByUserId(userId);
        return ResponseEntity.ok(baskets);
    }


    @Operation(summary = "Checkout the basket", description = "This endpoint allows a user to checkout the items in " +
            "their basket and create a spending record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Checkout successful, spending created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SpendingResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden, user is not authorized to checkout this basket",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Basket not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping("/{userId}/{basketId}/checkout")
    @Transactional
    public ResponseEntity<SpendingResponse> checkoutBasket(
            @PathVariable Long userId,
            @PathVariable Long basketId) {

        Basket basket = basketService.getBasketEntityById(basketId);

        if (!basket.getUser().getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }

        SpendingRequest spendingRequest = new SpendingRequest(
                userId,
                basket.getStore().getId(),
                convertBasketItems(basket.getItems()),
                calculateTotal(basket.getItems()),
                LocalDateTime.now()
        );


        SpendingResponse spending = spendingService.createSpending(spendingRequest);
        basketService.deleteBasket(basketId);

        return ResponseEntity.ok(spending);
    }



    private List<PurchasedItemRequest> convertBasketItems(List<BasketItem> items) {
        return items.stream()
                .map(item -> new PurchasedItemRequest(
                        null,
                        item.getStoreItem().getItem().getItemName(),
                        item.getPriceAtAddition(),
                        item.getQuantity(),
                        item.getStoreItem().getItem().getUnitType()
                ))
                .collect(Collectors.toList());
    }

    private float calculateTotal(List<BasketItem> items) {
        return (float) items.stream()
                .mapToDouble(item -> item.getPriceAtAddition() * item.getQuantity())
                .sum();
    }


    @PostMapping(value = "/users/{userId}/optimize", produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    @Operation(summary = "Optimize baskets", description = "Reorganize baskets to contain items at their cheapest " +
            "available price across all stores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Baskets optimized successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BasketResponse.class, type = "array"))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    public ResponseEntity<List<BasketResponse>> optimizeBaskets(
            @PathVariable Long userId
    ) {
        List<BasketResponse> optimizedBaskets = basketService.optimizeBaskets(userId);
        return ResponseEntity.ok(optimizedBaskets);
    }

}