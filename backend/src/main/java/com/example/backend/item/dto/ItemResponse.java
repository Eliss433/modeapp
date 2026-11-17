package com.example.backend.item.dto;

import com.example.backend.item.Category;
import com.example.backend.item.SubCategory;
import com.example.backend.item.Season;
import com.example.backend.item.Occasion;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ItemResponse(

    Long id,

    String name,

    String color,

    BigDecimal price,

    String description,

    Category category,

    SubCategory subCategory,

    Season season,

    Occasion occasion,

    boolean borrowed,

    LocalDate borrowedUntil,

    Long ownerId

) {}
