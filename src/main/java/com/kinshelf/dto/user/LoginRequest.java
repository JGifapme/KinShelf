package com.kinshelf.dto.user;

import jakarta.validation.constraints.NotBlank;
/// DTO pour le login avec le nom d'utilisateur et le mot de passe
public record LoginRequest(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
