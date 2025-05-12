package com.example.demo.controller;

import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.priceHistory.PriceHistoryResponse;
import com.example.demo.service.PriceHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/price-history")
@Validated
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    @Autowired
    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    @Operation(
            summary = "Get price history for a product",
            description = "Returns a list of price history entries for a specific store item, optionally filtered by storeId, startDate and endDate."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price history retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PriceHistoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping("/product/{itemId}")
    public ResponseEntity<List<PriceHistoryResponse>> getPriceHistoryForProduct(
            @PathVariable Long itemId,
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(priceHistoryService.getFilteredPriceHistory(itemId, storeId, startDate, endDate));
    }
}