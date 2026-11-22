
package com.example.backend.item;

import com.example.backend.item.dto.ItemRequest;
import com.example.backend.item.dto.ItemResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // Alle Items eines Users
    @GetMapping("/user/{userId}")
    public List<ItemResponse> getItemsByUser(
            @PathVariable Long userId) {

        return itemService.getItemsByUser(userId);
    }

    // Einzelnes Item abrufen
    @GetMapping("/{id}")
    public ItemResponse getItemById(
            @PathVariable Long id) {

        return itemService.getItemById(id);
    }

    // Neues Item erstellen
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse createItem(
            @Valid @RequestBody ItemRequest request,
            @RequestParam Long ownerId) {

        return itemService.createItem(request, ownerId);
    }
}
