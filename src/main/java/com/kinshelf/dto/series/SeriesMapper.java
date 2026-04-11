package com.kinshelf.dto.series;

import com.kinshelf.entities.Series;

public class SeriesMapper {

    public static SeriesResponseDTO toDTO(Series series) {
        if (series == null) return null;

        return new SeriesResponseDTO(
                series.getId(),
                series.getName(),
                series.getNumberOfVolumes(),
                series.getStatus()
        );
    }

    public static Series toEntity(SeriesCreateDTO dto) {
        if (dto == null) return null;

        return Series.builder()
                .name(dto.name())
                .numberOfVolumes(dto.numberOfVolumes())
                .status(dto.status())
                .build();
    }

    public static void updateEntity(Series series, SeriesCreateDTO dto) {
        if (series == null || dto == null) return;

        series.setName(dto.name());
        series.setNumberOfVolumes(dto.numberOfVolumes());
        series.setStatus(dto.status());
    }
}
