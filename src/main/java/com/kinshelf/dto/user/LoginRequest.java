package com.kinshelf.dto.user;

import jakarta.validation.constraints.NotBlank;
/// DTO pour le login avec le nom d'utilisateur et le mot de passe
public record LoginRequest(
        @NotBlank(message = "Le username ne peut pas être vide.")
        String username,
        @NotBlank(message = "Le mot de passe ne peut pas être vide.")
        String password
) {
}
