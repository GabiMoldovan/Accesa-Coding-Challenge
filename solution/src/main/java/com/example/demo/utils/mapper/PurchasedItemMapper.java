package com.example.demo.utils.mapper;

import com.example.demo.dto.purchasedItem.PurchasedItemResponse;
import com.example.demo.model.PurchasedItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PurchasedItemMapper {
    public static PurchasedItemResponse entityToDto(PurchasedItem purchasedItem) {
        return new PurchasedItemResponse(
                purchasedItem.getId(),
                purchasedItem.getSpending().getId(),
                purchasedItem.getItemName(),
                purchasedItem.getPricePerUnit(),
                purchasedItem.getUnits(),
                purchasedItem.getUnitType()
        );
    }

    public static List<PurchasedItemResponse> entityListToDto(List<PurchasedItem> purchasedItems) {
        return purchasedItems.stream().map(PurchasedItemMapper::entityToDto).collect(Collectors.toList());
    }
}
