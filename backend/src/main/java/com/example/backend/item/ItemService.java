package com.example.backend.item;


import com.example.user.User;
import com.example.user.UserRepository;

import org.springframework.stereotype.Service;

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

    // Alle Items anzeigen
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Einzelnes Item anzeigen
    public Item getItemById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() ->
                    new RuntimeException("Item not found"));
    }

    // Items eines Users anzeigen
    public List<Item> getItemsByUser(Long userId) {
        return itemRepository.findByOwnerId(userId);
    }

    // Neues Item erstellen
    public Item createItem(Item item, Long ownerId) {

        User owner = userRepository.findById(ownerId)
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        item.setOwner(owner);

        return itemRepository.save(item);
    }

    // Item löschen
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
