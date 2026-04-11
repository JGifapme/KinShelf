package com.kinshelf.dto.genre;

import com.kinshelf.entities.Genre;

public class GenreMapper {

    public static GenreResponseDTO toDTO(Genre genre) {
        if (genre == null) return null;

        return new GenreResponseDTO(
                genre.getId(),
                genre.getName()
        );
    }

    public static Genre toEntity(GenreCreateDTO dto) {
        if (dto == null) return null;

        return Genre.builder()
                .name(dto.name())
                .build();
    }

    public static void updateEntity(Genre genre, GenreCreateDTO dto) {
        if (genre == null || dto == null) return;

        genre.setName(dto.name());
    }
}
