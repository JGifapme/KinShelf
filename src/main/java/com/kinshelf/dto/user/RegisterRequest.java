package com.kinshelf.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
///  DTO utilisé lors de l'inscription d'un utilisateur, demande le username,
/// la date de naissance, l'email et le mot de passe
public record RegisterRequest(
        @NotBlank(message = "Le username est obligatoire")
        @Size(max = 150, message = "Le username ne peut dépasser 150 caractères.")
        String username,

        @NotNull(message = "La date de naissance est obligatoire")
        LocalDate dateOfBirth,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email n'est pas valide.")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 6, message = "Le mot de passe doit faire minimum 6 caractères.")
        String password
) {
}
