package com.example.demo.service;

import com.example.demo.dto.item.ItemRequest;
import com.example.demo.dto.item.ItemResponse;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepo;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepo itemRepository;

    public ItemService(ItemRepo itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemResponse createItem(ItemRequest request) {
        Item item = new Item();
        item.setItemName(request.itemName());
        item.setCategory(request.category());
        item.setBrand(request.brand());
        item.setUnitType(request.unitType());
        Item savedItem = itemRepository.save(item);
        return convertToResponse(savedItem);
    }

    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));
        return convertToResponse(item);
    }

    private ItemResponse convertToResponse(Item item) {
        return new ItemResponse(
                item.getId(),
                item.getItemName(),
                item.getCategory(),
                item.getBrand(),
                item.getUnitType()
        );
    }
}