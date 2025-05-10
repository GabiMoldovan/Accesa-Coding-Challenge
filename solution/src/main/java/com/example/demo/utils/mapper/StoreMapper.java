package com.example.demo.utils.mapper;

import com.example.demo.dto.store.StoreResponse;
import com.example.demo.model.Store;
import org.springframework.stereotype.Component;

@Component
public class StoreMapper {
    public static StoreResponse entityToDto(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getCompanyName(),
                store.getStoreItems() != null ? StoreItemMapper.entityListToDto(store.getStoreItems()) : null,
                store.getDiscounts() != null ? ItemDiscountMapper.entityListToDto(store.getDiscounts()) : null,
                store.getPriceHistoryEntries() != null ? PriceHistoryMapper.entityListToDto(store.getPriceHistoryEntries()) : null
        );
    }
}
