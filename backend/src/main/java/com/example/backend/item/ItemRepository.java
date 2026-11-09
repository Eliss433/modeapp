package com.example.backend.item;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository
        extends JpaRepository<Item, Long> {

    // Alle Items eines bestimmten Users
    List<Item> findByOwnerId(Long ownerId);

    // Alle Items einer Kategorie
    List<Item> findByCategory(Category category);

    // Alle verfügbaren Items
    List<Item> findByBorrowedFalse();
}
