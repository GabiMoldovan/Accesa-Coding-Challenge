package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.storeItem.StoreItemRequest;
import com.example.demo.dto.storeItem.StoreItemResponse;
import com.example.demo.dto.storeItemBestValueResponse.StoreItemBestValueResponse;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.service.StoreItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store-item")
public class StoreItemController {

    private final StoreItemService storeItemService;

    public StoreItemController(StoreItemService storeItemService) {
        this.storeItemService = storeItemService;
    }

    @Operation(summary = "Create a new store item", description = "This endpoint is used to create a item that's in a store." +
            "The details of the store item to be created are passed in the request body. " +
            "The response body contains the details of the created store item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Store item created successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request due to validation errors",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))})
    })
    @PostMapping
    public ResponseEntity<StoreItemResponse> createStoreItem(@Valid @RequestBody StoreItemRequest storeItemRequest) {
        StoreItemResponse createdStoreItem = storeItemService.createStoreItem(storeItemRequest);
        return ResponseEntity.ok(createdStoreItem);
    }


    @Operation(summary = "Get a store item by ID", description = "Retrieve a specific store item by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store item retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreItemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Store item not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<StoreItemResponse> getStoreItem(@PathVariable Long id) {
        StoreItemResponse storeItem = storeItemService.getStoreItemById(id);
        return ResponseEntity.ok(storeItem);
    }

    @Operation(summary = "Update a store item", description = "Partially update a store item by its ID using the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store item updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreItemResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Store item not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<StoreItemResponse> updateStoreItem(@PathVariable Long id,
                                                             @Valid @RequestBody StoreItemRequest storeItemRequest) {
        StoreItemResponse updatedItem = storeItemService.updateStoreItem(id, storeItemRequest);
        return ResponseEntity.ok(updatedItem);
    }


    @Operation(summary = "Delete a store item", description = "Delete a store item by its ID and return the deleted item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store item deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreItemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Store item not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<StoreItemResponse> deleteStoreItem(@PathVariable Long id) {
        StoreItemResponse deletedItem = storeItemService.deleteStoreItem(id);
        return ResponseEntity.ok(deletedItem);
    }

    @Operation(summary = "Get all store items by store ID", description = "Retrieve a list of all store items for a specific store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of store items retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreItemResponse.class))),
            @ApiResponse(responseCode = "404", description = "Store not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<StoreItemResponse>> getAllStoreItemsByStoreId(@PathVariable Long storeId) {
        List<StoreItemResponse> storeItems = storeItemService.getAllByStoreId(storeId);
        return ResponseEntity.ok(storeItems);
    }

    @Operation(summary = "Find the instance of a StoreItem with the best value per unit",
            description = "Returns the StoreItem with the highest price/unit for that item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "StoreItem found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = StoreItemBestValueResponse.class))),
            @ApiResponse(responseCode = "404", description = "The StoreItem doesn't exist",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/{storeItemId}/best-value")
    public ResponseEntity<StoreItemBestValueResponse> getBestValuePerUnit(@PathVariable Long storeItemId) {
        StoreItemBestValueResponse bestValueItem = storeItemService.findBestValuePerUnit(storeItemId);
        return ResponseEntity.ok(bestValueItem);
    }

}
