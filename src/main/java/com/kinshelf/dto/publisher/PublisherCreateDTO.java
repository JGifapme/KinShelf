package com.kinshelf.dto.publisher;

import jakarta.validation.constraints.NotBlank;

public record PublisherCreateDTO(
        @NotBlank(message = "Le nom est obligatoire")
        String name
) {}
