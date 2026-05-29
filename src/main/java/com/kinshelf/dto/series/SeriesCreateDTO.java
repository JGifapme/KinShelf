package com.kinshelf.dto.series;

import com.kinshelf.entities.SeriesStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
/// DTO qui donne les infos nécessaires pour créer une série
/// Les messages après les validations permettent de lancer une MethodArgumentNotValidException contenant le message
public record SeriesCreateDTO(
        @NotBlank(message = "Le nom est obligatoire")
        @Size(max = 150, message = "Le nom de la catégorie ne peut dépasser 150 caractères.")
        String name,
        SeriesStatus status
) {}
