package com.example.backend.combination.dto;

import com.example.backend.item.Occasion;
import com.example.backend.item.Season;

import jakarta.validation.constraints.*;
import java.util.List;

public record CombinationRequest(

        @NotBlank(message = "Name darf nicht leer sein")
        String name,

        @Size(max = 1000)
        String description,

        Occasion occasion,

        Season season,

        @NotEmpty(message = "Mindestens ein Item ist erforderlich")
        List<Long> itemIds

) {}