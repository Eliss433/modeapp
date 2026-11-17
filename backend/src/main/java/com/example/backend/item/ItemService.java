package com.example.backend.item;


import com.example.backend.item.dto.*;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(
            ItemRepository itemRepository,
            UserRepository userRepository) {

        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

     // Item erstellen
    @Transactional
    public ItemResponse createItem(ItemRequest request, Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        Item item = new Item();

        item.setName(request.name());
        item.setColor(request.color());
        item.setPrice(request.price());
        item.setDescription(request.description());
        item.setCategory(request.category());
        item.setSubCategory(request.subCategory());
        item.setSeason(request.season());
        item.setOccasion(request.occasion());

        item.setOwner(owner);
        item.setBorrowed(false);

        Item savedItem = itemRepository.save(item);

        return mapToResponse(savedItem);
    }

    // Entity in Response DTO umwandeln
    private ItemResponse mapToResponse(Item item) {

        return new ItemResponse(
            item.getId(),
            item.getName(),
            item.getColor(),
            item.getPrice(),
            item.getDescription(),
            item.getCategory(),
            item.getSubCategory(),
            item.getSeason(),
            item.getOccasion(),
            item.isBorrowed(),
            item.getBorrowedUntil(),
            item.getOwner().getId()
        );
    }

    // Alle Items eines Users anzeigen
    @Transactional(readOnly = true)
    public List<ItemResponse> getItemsByUser(Long userId) {

        return itemRepository.findByOwnerId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // Einzelnes Item anzeigen
    @Transactional(readOnly = true)
    public ItemResponse getItemById(Long id) {

        Item item = itemRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Item not found"));

        return mapToResponse(item);
    }
}
