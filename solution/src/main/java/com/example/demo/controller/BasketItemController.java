package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.basket.BasketRequest;
import com.example.demo.dto.basketItem.BasketItemRequest;
import com.example.demo.dto.basketItem.BasketItemResponse;
import com.example.demo.dto.updateBasketItemQuantityRequest.UpdateStoreItemQuantityRequest;
import com.example.demo.model.Basket;
import com.example.demo.service.BasketItemService;
import com.example.demo.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/basket-items")
@Validated
public class BasketItemController {
    private final BasketItemService basketItemService;
    private final BasketService basketService;

    @Autowired
    public BasketItemController(BasketItemService basketItemService,
                                BasketService basketService) {
        this.basketItemService = basketItemService;
        this.basketService = basketService;
    }


    @Operation(summary = "Add an item to the user's basket", description = "This endpoint allows a user to add an " +
            "item to their basket. A new basket will be created if none exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item successfully added to the basket",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BasketItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request due to validation errors",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping
    @Transactional
    public ResponseEntity<BasketItemResponse> addItemToBasket(
            @RequestParam Long userId,
            @RequestParam Long storeId,
            @Valid @RequestBody BasketItemRequest request) {

        Basket basket = basketService.findByUserIdAndStoreId(userId, storeId)
                .orElseGet(() -> createNewBasket(userId, storeId));

        BasketItemRequest newRequest = new BasketItemRequest(
                basket.getId(),
                request.storeItemId(),
                request.quantity()
        );

        return ResponseEntity.ok(basketItemService.addItemToBasket(newRequest));
    }


    @Operation(summary = "Get basket item by ID", description = "This endpoint allows fetching the details " +
            "of a specific basket item by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Basket item retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BasketItemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Basket item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<BasketItemResponse> getBasketItemById(@PathVariable Long id) {
        return ResponseEntity.ok(basketItemService.getBasketItemById(id));
    }


    @Operation(summary = "Delete a basket item", description = "This endpoint allows deleting an item from the " +
            "basket by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Basket item deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BasketItemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Basket item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<BasketItemResponse> deleteBasketItem(@PathVariable Long id) {
        BasketItemResponse response = basketItemService.getBasketItemById(id);
        basketItemService.removeItemFromBasket(id);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Update the quantity of a basket item", description = "This endpoint allows updating the " +
            "quantity of an existing item in the basket.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Basket item quantity updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = BasketItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request due to validation errors",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Basket item not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PatchMapping("/{id}")
    @Schema(description = "Update quantity of an item in the basket")
    public ResponseEntity<BasketItemResponse> updateBasketItemQuantity(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStoreItemQuantityRequest updateRequest) {
        basketItemService.updateItemQuantity(id, updateRequest.quantity());
        BasketItemResponse updatedItem = basketItemService.getBasketItemById(id);
        return ResponseEntity.ok(updatedItem);
    }



    private Basket createNewBasket(Long userId, Long storeId) {
        BasketRequest basketRequest = new BasketRequest(
                userId,
                storeId,
                Collections.emptyList()
        );
        return basketService.createBasketEntity(basketRequest);
    }
}
