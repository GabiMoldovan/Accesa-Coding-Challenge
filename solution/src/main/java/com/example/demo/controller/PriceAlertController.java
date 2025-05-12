package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.priceAlert.PriceAlertRequest;
import com.example.demo.dto.priceAlert.PriceAlertResponse;
import com.example.demo.dto.priceAlertPatchRequest.PriceAlertPatchRequest;
import com.example.demo.service.PriceAlertService;
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

import java.util.List;

@RestController
@RequestMapping("/price-alerts")
@Validated
public class PriceAlertController {
    private final PriceAlertService priceAlertService;

    @Autowired
    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @Operation(summary = "Create a price alert", description = "Creates a new price alert for a user on a specific item.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price alert created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceAlertResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PostMapping
    public ResponseEntity<PriceAlertResponse> createAlert(@RequestBody @Valid PriceAlertRequest request) {
        return ResponseEntity.ok(priceAlertService.createAlert(request));
    }


    @Operation(summary = "Update a price alert", description = "Updates the target price for an existing price alert.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price alert updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceAlertResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Price alert not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PriceAlertResponse> updateAlert(@PathVariable Long id,
                                                          @RequestBody @Valid PriceAlertPatchRequest request) {
        return ResponseEntity.ok(priceAlertService.updateAlert(id, request.targetPrice()));
    }


    @Operation(summary = "Delete a price alert", description = "Deletes a price alert by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price alert deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceAlertResponse.class))),
            @ApiResponse(responseCode = "404", description = "Price alert not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<PriceAlertResponse> deleteAlert(@PathVariable Long id) {
        return ResponseEntity.ok(priceAlertService.deleteAlert(id));
    }


    @Operation(summary = "Get alerts by user ID", description = "Fetches all price alerts created by a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerts retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceAlertResponse.class))),
            @ApiResponse(responseCode = "404", description = "No alerts found for the user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PriceAlertResponse>> getAlertsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(priceAlertService.getAlertsByUserId(userId));
    }
}
