package com.example.demo.utils.mapper;

import com.example.demo.dto.itemDiscount.ItemDiscountResponse;
import com.example.demo.model.ItemDiscount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemDiscountMapper {
    public static ItemDiscountResponse entityToDto(ItemDiscount itemDiscount) {
        return new ItemDiscountResponse(
                itemDiscount.getId(),
                itemDiscount.getStoreItem().getId(),
                itemDiscount.getStore().getId(),
                itemDiscount.getOldPrice(),
                itemDiscount.getDiscountPercentage(),
                itemDiscount.getStartDate(),
                itemDiscount.getEndDate()
        );
    }

    public static List<ItemDiscountResponse> entityListToDto(List<ItemDiscount> discountedItems) {
        return discountedItems.stream().map(ItemDiscountMapper::entityToDto).collect(Collectors.toList());
    }
}
