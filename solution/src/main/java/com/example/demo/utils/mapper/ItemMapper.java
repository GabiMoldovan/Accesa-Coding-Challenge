package com.example.demo.utils.mapper;

import com.example.demo.dto.item.ItemResponse;
import com.example.demo.model.Item;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public static ItemResponse entityToDto(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getItemName(),
                item.getCategory(),
                item.getBrand(),
                item.getUnitType()
        );
    }

    public static List<ItemResponse> entityListToDto(List<Item> items) {
        return items.stream().map(ItemMapper::entityToDto).collect(Collectors.toList());
    }
}
