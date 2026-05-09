package com.kinshelf.dto.series;

import com.kinshelf.entities.SeriesStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SeriesCreateDTO(
        @NotBlank(message = "Le nom est obligatoire")
        String name,
        SeriesStatus status
) {}
