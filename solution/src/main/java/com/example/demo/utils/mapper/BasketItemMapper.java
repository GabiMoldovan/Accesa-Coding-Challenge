package com.example.demo.utils.mapper;

import com.example.demo.dto.basketItem.BasketItemResponse;
import com.example.demo.model.BasketItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasketItemMapper {
    public static BasketItemResponse entityToDto(BasketItem item) {
        return new BasketItemResponse(
                item.getId(),
                item.getBasket().getId(),
                item.getStoreItem().getId(),
                item.getQuantity(),
                item.getPriceAtAddition()
        );
    }

    public static List<BasketItemResponse> entityListToDto(List<BasketItem> items) {
        return items.stream()
                .map(BasketItemMapper::entityToDto)
                .collect(Collectors.toList());
    }
}
