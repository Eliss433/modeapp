package com.example.backend.combination.dto;

import com.example.backend.item.Occasion;
import com.example.backend.item.Season;

import java.util.List;

public record CombinationResponse(

        Long id,
        String name,
        String description,
        Occasion occasion,
        Season season,
        Long ownerId,
        List<Long> itemIds

) {}