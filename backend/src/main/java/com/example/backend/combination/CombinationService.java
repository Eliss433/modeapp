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
    /** Ruft alle Kombinationen eines Nutzers ab. */
    @Transactional(readOnly = true)
    public List<CombinationResponse> getCombinationsByUser(Long ownerId) {

        return combinationRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    /** Ruft eine einzelne Kombination anhand ihrer ID ab. */
    @Transactional(readOnly = true)
    public CombinationResponse getCombinationById(Long id) {

        Combination combination = combinationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Combination not found"));

        return mapToResponse(combination);
    }
    /** Bearbeitet eine bestehende Kombination. */
    @Transactional
    public CombinationResponse updateCombination(
            Long id, CombinationRequest request, Long ownerId) {

        // Bestehende Kombination suchen
        Combination combination = combinationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Combination not found"));

        // Besitzer der Kombination überprüfen
        if (!combination.getOwner().getId().equals(ownerId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access denied");
        }

        // Kleidungsstücke suchen
        List<Item> items = itemRepository.findAllById(request.itemIds());

        // Überprüfen, ob alle Items existieren
        if (items.size() != request.itemIds().size()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid item IDs");
        }

        // Besitzer der Items überprüfen
        for (Item item : items) {
            if (!item.getOwner().getId().equals(ownerId)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Item belongs to another user");
            }
        }

        // Bestehende Kombination verändern
        combination.setName(request.name());
        combination.setDescription(request.description());
        combination.setOccasion(request.occasion());
        combination.setSeason(request.season());
        combination.setItems(items);

        // Änderungen speichern
        Combination updatedCombination =
                combinationRepository.save(combination);

        return mapToResponse(updatedCombination);
    }
    /** Löscht eine bestehende Kombination. */
    @Transactional
    public void deleteCombination(Long id, Long ownerId) {

        // Kombination suchen
        Combination combination = combinationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Combination not found"));

        // Besitzer überprüfen
        if (!combination.getOwner().getId().equals(ownerId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access denied");
        }

        // Kombination löschen
        combinationRepository.delete(combination);
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

    React / Postman
    React / Postman
       ↕ HTTP
    CombinationController
           ↕
    CombinationService
           ↕
    CombinationRepository
           ↕
    PostgreSQL

*/
}
