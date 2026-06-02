package com.kinshelf.dto.bookAuthor;

import com.kinshelf.entities.AuthorRole;
import jakarta.validation.constraints.NotNull;
///  DTO de création pour la table de liaison entre les auteurs et leurs livres
public record BookAuthorCreateDTO(

        @NotNull(message = "Id manquant pour l'auteur.")
        Long authorId,

        @NotNull(message = "L'auteur doit avoir un role.")
        AuthorRole role

) {}
