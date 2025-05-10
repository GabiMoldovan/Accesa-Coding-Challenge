package com.example.demo.utils.mapper;

import com.example.demo.dto.storeItem.StoreItemResponse;
import com.example.demo.model.StoreItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreItemMapper {
    public static StoreItemResponse entityToDto(StoreItem storeItem) {
        return new StoreItemResponse(
                storeItem.getId(),
                storeItem.getStore().getId(),
                storeItem.getItem().getId(),
                storeItem.getPricePerUnit(),
                storeItem.getUnits(),
                storeItem.getCurrency(),
                storeItem.getTotalPrice()
        );
    }

    public static List<StoreItemResponse> entityListToDto(List<StoreItem> storeItems) {
        return storeItems.stream().map(StoreItemMapper::entityToDto).collect(Collectors.toList());
    }
}
