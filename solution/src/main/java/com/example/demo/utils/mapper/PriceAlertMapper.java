package com.example.demo.utils.mapper;

import com.example.demo.dto.priceAlert.PriceAlertResponse;
import com.example.demo.model.PriceAlert;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PriceAlertMapper {
    public static PriceAlertResponse entityToDto(PriceAlert priceAlert) {
        return new PriceAlertResponse(
                priceAlert.getId(),
                priceAlert.getUser().getId(),
                priceAlert.getStoreItem().getId(),
                priceAlert.getTargetPrice()
        );
    }

    public static List<PriceAlertResponse> entityListToDto(List<PriceAlert> priceAlertList) {
        return priceAlertList.stream().map(PriceAlertMapper::entityToDto).collect(Collectors.toList());
    }
}
