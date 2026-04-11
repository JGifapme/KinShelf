package com.kinshelf.dto.author;

import com.kinshelf.entities.Author;

public class AuthorMapper {

    public static AuthorResponseDTO toDTO(Author author) {
        if (author == null) {
            return null;
        }

        return new AuthorResponseDTO(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getFirstName() + " " + author.getLastName()
        );
    }

    public static Author toEntity(AuthorCreateDTO dto) {
        if (dto == null) {
            return null;
        }

        return Author.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .build();
    }

    public static void updateEntity(Author author, AuthorCreateDTO dto) {
        if (author == null || dto == null) {
            return;
        }

        author.setFirstName(dto.firstName());
        author.setLastName(dto.lastName());
    }
}