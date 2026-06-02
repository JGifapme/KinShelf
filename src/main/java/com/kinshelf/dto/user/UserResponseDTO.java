package com.kinshelf.dto.user;

import java.time.LocalDate;
/// DTO qui retourne toutes les infos publiques de l'utilisateur
public record UserResponseDTO(
        Long id,
        String username,
        String slug,
        LocalDate dateOfBirth
) {}