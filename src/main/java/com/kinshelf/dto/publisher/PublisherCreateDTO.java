package com.kinshelf.dto.publisher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/// DTO qui donne les infos nécessaires pour créer un éditeur, juste le nom
/// Les messages après les validations permettent de lancer une MethodArgumentNotValidException contenant le message
public record PublisherCreateDTO(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 150, message = "Le nom de la catégorie ne peut dépasser 150 caractères.")
        String name
) {}
