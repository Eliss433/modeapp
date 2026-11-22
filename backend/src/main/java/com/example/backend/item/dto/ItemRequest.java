package com.example.backend.item.dto;

import com.example.backend.item.Category;
import com.example.backend.item.SubCategory;
import com.example.backend.item.Season;
import com.example.backend.item.Occasion;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ItemRequest(

    @NotBlank(message = "Name darf nicht leer sein")
    String name,

    String color,

    @DecimalMin(value = "0.0",
        message = "Preis darf nicht negativ sein")
    BigDecimal price,

    @Size(max = 1000,
        message = "Beschreibung darf maximal 1000 Zeichen haben")
    String description,

    @NotNull(message = "Kategorie ist erforderlich")
    Category category,

    @NotNull(message = "Unterkategorie ist erforderlich")
    SubCategory subCategory,

    @NotNull(message = "Jahreszeit ist erforderlich")
    Season season,

    @NotNull(message = "Anlass ist erforderlich")
    Occasion occasion

) {}
