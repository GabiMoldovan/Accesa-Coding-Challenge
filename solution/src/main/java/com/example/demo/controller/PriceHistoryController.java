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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            summary = "Get price history filtered by store, category, or brand",
            description = "Returns a list of price history entries filterable by store, product category, or brand."
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
    @GetMapping("/dynamic-price-history")
    public ResponseEntity<List<PriceHistoryResponse>> getPriceHistory(
            @RequestParam(required = false) Long storeId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand) {
        return ResponseEntity.ok(priceHistoryService.getFilteredPriceHistoryByStoreCategoryBrand(storeId, category, brand));
    }

}