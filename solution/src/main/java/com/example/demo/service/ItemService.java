package com.example.demo.service;

import com.example.demo.dto.item.ItemRequest;
import com.example.demo.dto.item.ItemResponse;
import com.example.demo.dto.itemPatchRequest.ItemPatchRequest;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.model.Item;
import com.example.demo.repository.ItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Transactional
    public ItemResponse updateItem(Long id, ItemPatchRequest request) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        if (request.itemName() != null) item.setItemName(request.itemName());
        if (request.category() != null) item.setCategory(request.category());
        if (request.brand() != null) item.setBrand(request.brand());
        if (request.unitType() != null) item.setUnitType(request.unitType());

        return convertToResponse(itemRepository.save(item));
    }

    public ItemResponse deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        itemRepository.delete(item);
        return convertToResponse(item);
    }

    public List<ItemResponse> getAllItems() {
        return itemRepository.findAll().stream()
                .map(this::convertToResponse)
                .toList();
    }

}