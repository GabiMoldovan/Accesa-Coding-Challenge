package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.store.StoreRequest;
import com.example.demo.dto.store.StoreResponse;
import com.example.demo.dto.storePatchRequest.StorePatchRequest;
import com.example.demo.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@Validated
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @Operation(summary = "Create a new store", description = "Creates a store and returns its details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<StoreResponse> createStore(@Valid @RequestBody StoreRequest request) {
        return ResponseEntity.ok(storeService.createStore(request));
    }

    @Operation(summary = "Get a store by ID", description = "Retrieves a store by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreResponse.class))),
            @ApiResponse(responseCode = "404", description = "Store not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable Long id) {
        StoreResponse storeResponse = storeService.getStoreById(id);
        return ResponseEntity.ok(storeResponse);
    }


    @Operation(summary = "Update a store's company name", description = "Updates only the company name of a store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Store not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<StoreResponse> updateStore(
            @PathVariable Long id,
            @RequestBody @Valid StorePatchRequest request
    ) {
        StoreResponse updatedStore = storeService.updateStore(id, request.companyName());
        return ResponseEntity.ok(updatedStore);
    }

    @Operation(summary = "Delete a store", description = "Deletes a store by ID and returns the deleted store.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Store deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = StoreResponse.class))),
            @ApiResponse(responseCode = "404", description = "Store not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<StoreResponse> deleteStore(@PathVariable Long id) {
        StoreResponse deletedStore = storeService.deleteStore(id);
        return ResponseEntity.ok(deletedStore);
    }


}