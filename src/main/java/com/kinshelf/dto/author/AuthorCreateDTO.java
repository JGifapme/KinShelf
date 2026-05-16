package com.kinshelf.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorCreateDTO(

        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 150, message = "Le nom ne peut dépasser 150 caractères.")
        String name

) {}
