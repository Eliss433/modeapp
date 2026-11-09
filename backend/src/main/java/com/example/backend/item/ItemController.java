package com.example.backend.item;


import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Alle Items
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    // Einzelnes Item
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemService.getItemById(id);
    }

    // Items eines Users
    @GetMapping("/user/{userId}")
    public List<Item> getItemsByUser(
            @PathVariable Long userId) {

        return itemService.getItemsByUser(userId);
    }

    // Neues Item erstellen
    @PostMapping
    public Item createItem(
            @RequestBody Item item,
            @RequestParam Long ownerId) {

        return itemService.createItem(item, ownerId);
    }

    // Item löschen
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);
    }
}
