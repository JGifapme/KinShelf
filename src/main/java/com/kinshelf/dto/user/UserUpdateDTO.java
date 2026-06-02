package com.kinshelf.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
/// DTO pour mettre à jour les infos de l'utilisateur connecté via sa page profil
/// Le mot de passe n'est pas obligatoire, l'ancien est gardé si vide
public record UserUpdateDTO(
        @NotBlank(message = "Le username est obligatoire")
        @Size(max = 150)
        String username,

        @NotNull(message = "La date de naissance est obligatoire")
        LocalDate dateOfBirth,

        @Email
        @NotBlank(message = "L'email est obligatoire")
        String email,

        @Size(min = 6, message = "Le mot de passe doit faire minimum 6 caractères.")
        String password // nullable — on ne le change que s'il est fourni
) {}
