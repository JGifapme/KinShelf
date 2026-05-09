package com.kinshelf.dto.author;

import jakarta.validation.constraints.NotBlank;

public record AuthorCreateDTO(

        @NotBlank(message = "Le prénom est obligatoire")
        String firstName,

        @NotBlank(message = "Le nom est obligatoire")
        String lastName

) {}
