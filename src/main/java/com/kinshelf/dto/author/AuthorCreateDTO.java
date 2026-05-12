package com.kinshelf.dto.author;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorCreateDTO(

        //@NotBlank(message = "Le prénom est obligatoire") Pour certains auteurs, on utilise que le pseudo
        @Size(max = 75, message = "Le prénom ne peut dépasser 75 caractères.")
        String firstName,

        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 75, message = "Le nom ne peut dépasser 75 caractères.")
        String lastName

) {}
