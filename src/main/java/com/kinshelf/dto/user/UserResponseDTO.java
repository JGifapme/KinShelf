package com.kinshelf.dto.user;

import java.time.LocalDate;

public record UserResponseDTO(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth
) {}