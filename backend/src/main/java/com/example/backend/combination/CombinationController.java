package com.example.backend.combination;

import com.example.backend.combination.dto.CombinationRequest;
import com.example.backend.combination.dto.CombinationResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/combinations")
public class CombinationController {

    private final CombinationService combinationService;

    public CombinationController(CombinationService combinationService) {
        this.combinationService = combinationService;
    }

    /** Neue Kombination erstellen. */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CombinationResponse createCombination(
            @Valid @RequestBody CombinationRequest request,
            @RequestParam Long ownerId) {

        return combinationService.createCombination(request, ownerId);
    }

    /** Alle Kombinationen eines Nutzers abrufen. */
    @GetMapping("/user/{userId}")
    public List<CombinationResponse> getCombinationsByUser(
            @PathVariable Long userId) {

        return combinationService.getCombinationsByUser(userId);
    }

    /** Einzelne Kombination abrufen. */
    @GetMapping("/{id}")
    public CombinationResponse getCombinationById(
            @PathVariable Long id) {

        return combinationService.getCombinationById(id);
    }

    /** Kombination bearbeiten. */
    @PutMapping("/{id}")
    public CombinationResponse updateCombination(
            @PathVariable Long id,
            @Valid @RequestBody CombinationRequest request,
            @RequestParam Long ownerId) {

        return combinationService.updateCombination(id, request, ownerId);
    }

    /** Kombination löschen. */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCombination(
            @PathVariable Long id,
            @RequestParam Long ownerId) {

        combinationService.deleteCombination(id, ownerId);
    }
}