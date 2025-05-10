package com.example.demo.utils.mapper;

import com.example.demo.dto.priceHistory.PriceHistoryResponse;
import com.example.demo.model.PriceHistory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceHistoryMapper {
    public static PriceHistoryResponse entityToDto(PriceHistory priceHistory) {
        return new PriceHistoryResponse(
                priceHistory.getId(),
                priceHistory.getStoreItem().getId(),
                priceHistory.getStore().getId(),
                priceHistory.getDate(),
                priceHistory.getPrice()
        );
    }

    public static List<PriceHistoryResponse> entityListToDto(List<PriceHistory> priceHistoryList) {
        return priceHistoryList.stream().map(PriceHistoryMapper::entityToDto).collect(Collectors.toList());
    }
}
