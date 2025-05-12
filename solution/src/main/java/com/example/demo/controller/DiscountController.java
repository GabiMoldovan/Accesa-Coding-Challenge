package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.itemDiscount.ItemDiscountRequest;
import com.example.demo.dto.itemDiscount.ItemDiscountResponse;
import com.example.demo.service.ItemDiscountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/discounts")
@Validated
public class DiscountController {
    private final ItemDiscountService discountService;

    @Autowired
    public DiscountController(ItemDiscountService discountService) {
        this.discountService = discountService;
    }

    @Operation(summary = "Create a new discount", description = "This endpoint allows creating a new discount for an item. The details of the discount are passed in the request body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDiscountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<ItemDiscountResponse> createDiscount(@RequestBody @Validated ItemDiscountRequest request) {
        return ResponseEntity.ok(discountService.createDiscount(request));
    }

    @Operation(summary = "Update an existing discount", description = "This endpoint allows updating the details of an existing discount.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDiscountResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Discount not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PatchMapping(value = "/{id}")
    public ResponseEntity<ItemDiscountResponse> updateDiscount(
            @PathVariable Long id,
            @RequestBody ItemDiscountRequest request) {
        return ResponseEntity.ok(discountService.updateDiscount(id, request));
    }

    @Operation(summary = "Delete an existing discount", description = "This endpoint allows deleting a discount by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discount deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDiscountResponse.class))),
            @ApiResponse(responseCode = "404", description = "Discount not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ItemDiscountResponse> deleteDiscount(@PathVariable Long id) {
        return ResponseEntity.ok(discountService.deleteDiscount(id));
    }

    @Operation(summary = "Get all active discounts", description = "Fetches all the active discounts for items at the current time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active discounts retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDiscountResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/active")
    public ResponseEntity<List<ItemDiscountResponse>> getActiveDiscounts() {
        return ResponseEntity.ok(discountService.getActiveDiscounts(LocalDateTime.now()));
    }

    @Operation(summary = "Get top best percentage discounts", description = "Fetches the top discounts based on the specified count.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Top discounts retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDiscountResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/best-percentage-discounts")
    public ResponseEntity<List<ItemDiscountResponse>> getBestDiscounts(
            @RequestParam(defaultValue = "25") int count) {
        return ResponseEntity.ok(discountService.getTopDiscounts(count));
    }

    @Operation(summary = "Get discounts for a specific item", description = "Fetches all discounts available for a specific item by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Discounts for the item retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDiscountResponse.class))),
            @ApiResponse(responseCode = "404", description = "No discounts found for the item",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/by-item")
    public ResponseEntity<List<ItemDiscountResponse>> getDiscountsForItem(@RequestParam Long itemId) {
        return ResponseEntity.ok(discountService.getDiscountsForItem(itemId));
    }


    @Operation(summary = "Get maximum discount per item", description = "Fetches the maximum discount percentage for each item across all stores, considering active discounts.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Maximum discounts per item retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ItemDiscountResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/all-items-discounts-with-max-discount-per-item")
    public ResponseEntity<List<ItemDiscountResponse>> getMaxDiscountPerItem() {
        LocalDateTime now = LocalDateTime.now();
        List<ItemDiscountResponse> activeDiscounts = discountService.getActiveDiscountsWithUniqueItems(now);

        Map<Long, ItemDiscountResponse> maxDiscountMap = new HashMap<>();
        for (ItemDiscountResponse discount : activeDiscounts) {
            Long itemId = discount.itemId();
            maxDiscountMap.merge(itemId, discount, (existing, current) ->
                    current.discountPercentage() > existing.discountPercentage() ? current : existing
            );
        }

        List<ItemDiscountResponse> result = new ArrayList<>(maxDiscountMap.values());
        return ResponseEntity.ok(result);
    }

}