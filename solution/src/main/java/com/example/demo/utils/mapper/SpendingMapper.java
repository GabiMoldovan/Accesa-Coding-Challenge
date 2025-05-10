package com.example.demo.utils.mapper;

import com.example.demo.dto.spending.SpendingResponse;
import com.example.demo.model.Spending;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpendingMapper {
    public static SpendingResponse entityToDto(Spending spending) {
        return new SpendingResponse(
                spending.getId(),
                spending.getUser().getId(),
                spending.getStore().getId(),
                spending.getPurchasedItems() != null ? PurchasedItemMapper.entityListToDto(spending.getPurchasedItems()) : null,
                spending.getTotalPrice(),
                spending.getPurchaseDate()
        );
    }

    public static List<SpendingResponse> entityListToDto(List<Spending> spendings) {
        return spendings.stream().map(SpendingMapper::entityToDto).collect(Collectors.toList());
    }
}
