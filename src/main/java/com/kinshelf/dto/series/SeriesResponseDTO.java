package com.kinshelf.dto.series;

import com.kinshelf.entities.SeriesStatus;
/// DTO qui retourne l'id, le slug et le nom d'une série
public record SeriesResponseDTO(
        Long id,
        String name,
        String slug,
        SeriesStatus status
) {}
