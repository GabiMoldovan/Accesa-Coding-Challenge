package com.example.demo.utils.mapper;

import com.example.demo.dto.basket.BasketResponse;
import com.example.demo.model.Basket;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BasketMapper {
    public static BasketResponse entityToDto(Basket basket) {
        return new BasketResponse(
                basket.getId(),
                basket.getUser().getId(),
                basket.getStore().getId(),
                basket.getItems() != null ? BasketItemMapper.entityListToDto(basket.getItems()) : null
        );
    }

    public static List<BasketResponse> entityListToDto(List<Basket> baskets) {
        return baskets.stream().map(BasketMapper::entityToDto).collect(Collectors.toList());
    }
}