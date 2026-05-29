package com.kinshelf.dto.genre;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
/// DTO qui donne les infos nécessaires pour créer un genre, juste le nom
/// Les messages après les validations permettent de lancer une MethodArgumentNotValidException contenant le message
public record GenreCreateDTO(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 150, message = "Le nom du genre ne peut dépasser 150 caractères.")
        String name
) {}
