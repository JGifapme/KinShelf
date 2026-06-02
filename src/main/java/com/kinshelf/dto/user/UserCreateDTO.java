package com.kinshelf.dto.user;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
/// DTO utilisé lors de la création d'un utilisateur, après avoir récupéré les infos de l'utilisateur via
/// le registerRequest DTO, on lui ajoute ses roles (Spring security) avant de le sauvegarder en base de données
public record UserCreateDTO(

        @NotBlank(message = "Le username est obligatoire")
        @Size(max = 150, message = "Le username ne peut dépasser 150 caractères.")
        String username,

        @NotNull(message = "La date de naissance est obligatoire")
        LocalDate dateOfBirth,

        @Email(message = "L'email n'est pas valide.")
        @NotBlank(message = "L'email est obligatoire")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 6, message = "Le mot de passe doit faire minimum 6 caractères.")
        String password,

        List<String> userRoles
) {}