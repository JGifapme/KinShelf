package com.kinshelf.dto.series;

import com.kinshelf.entities.SeriesStatus;

public record SeriesResponseDTO(
        Long id,
        String name,
        Integer numberOfVolumes,
        SeriesStatus status
) {}
