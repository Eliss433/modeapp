package com.example.backend.combination;

import com.example.backend.combination.dto.CombinationRequest;
import com.example.backend.combination.dto.CombinationResponse;
import com.example.backend.item.Item;
import com.example.backend.item.ItemRepository;
import com.example.backend.user.User;
import com.example.backend.user.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
/*
bereits vorhandene Kleidungsstücke aus DB aussuchen und miteinander verknüpfen */
@Service
//Spring stellt die drei Repositories automatisch zur Verfügung
public class CombinationService {

    private final CombinationRepository combinationRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public CombinationService(
            CombinationRepository combinationRepository,
            ItemRepository itemRepository,
            UserRepository userRepository) {

        this.combinationRepository = combinationRepository;
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    /** Erstellt eine neue Kombination. */
    @Transactional
    public CombinationResponse createCombination(
            CombinationRequest request, Long ownerId) {
        //Besitzer in der Datenbank suchen
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"));
        //Kleidungsstücke in der Datenbank suchen

        List<Item> items = itemRepository.findAllById(request.itemIds());
        //Überprüfen, ob alle Items existieren

        if (items.size() != request.itemIds().size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid item IDs");
        }
        //Überprüfen, wem die Items gehören

        for (Item item : items) {
            if (!item.getOwner().getId().equals(ownerId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Item belongs to another user");
            }
        }

        //Erstellen ein neues Objekt der Klasse Combination.java
        //-->den Namen aus dem Request lesen und ihn im Combination-Objekt setzen

        Combination combination = new Combination();

        combination.setName(request.name());
        combination.setDescription(request.description());
        combination.setOccasion(request.occasion());
        combination.setSeason(request.season());
        combination.setOwner(owner);
        combination.setItems(items);

        Combination savedCombination =
                combinationRepository.save(combination);

        return mapToResponse(savedCombination);
    }

    // Entity in Response DTO umwandeln
    private CombinationResponse mapToResponse(Combination combination) {

        return new CombinationResponse(
                combination.getId(),
                combination.getName(),
                combination.getDescription(),
                combination.getOccasion(),
                combination.getSeason(),
                combination.getOwner().getId(),
                combination.getItems()
                        .stream()
                        .map(Item::getId)
                        .toList()
        );
    }
/*
     React / Postman
           │
           ▼
    CombinationRequest
           │
           ▼
    CombinationService
           │
           ├── Besitzer suchen
           ├── Kleidungsstücke suchen
           ├── Daten überprüfen
           ├── Combination-Objekt erstellen
           └── Kombination speichern
           │
           ▼
    CombinationResponse
           │
           ▼
    React / Postman*/
}
