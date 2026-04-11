package com.kinshelf.dto.book;

import com.kinshelf.entities.AuthorRole;
import jakarta.validation.constraints.NotNull;

public record BookAuthorCreateDTO(

        @NotNull
        Integer authorId,

        @NotNull
        AuthorRole role

) {}
