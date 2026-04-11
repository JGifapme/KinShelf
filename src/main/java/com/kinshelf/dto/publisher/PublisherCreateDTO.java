package com.kinshelf.dto.publisher;

import jakarta.validation.constraints.NotBlank;

public record PublisherCreateDTO(
        @NotBlank
        String name
) {}
