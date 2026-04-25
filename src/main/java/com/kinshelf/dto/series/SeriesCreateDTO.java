package com.kinshelf.dto.series;

import com.kinshelf.entities.SeriesStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SeriesCreateDTO(
        @NotBlank
        String name,
        SeriesStatus status
) {}
