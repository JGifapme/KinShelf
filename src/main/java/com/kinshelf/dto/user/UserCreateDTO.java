package com.kinshelf.dto.user;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UserCreateDTO(

        @NotBlank(message = "Le prénom est obligatoire")
        @Size(max = 75, message = "Le prénom ne peut dépasser 75 caractères.")
        String firstName,

        @NotBlank(message = "Le nom de famille est obligatoire")
        @Size(max = 75, message = "Le nom de famille ne peut dépasser 75 caractères.")
        String lastName,

        @NotNull(message = "La date de naissance est obligatoire")
        LocalDate dateOfBirth,

        @Email(message = "L'email n'est pas valide.")
        @NotBlank(message = "L'email est obligatoire")
        String email,

        @NotBlank(message = "Le mot de passe est obligatoire")
        @Size(min = 6, message = "Le mot de passe doit faire minimum 6 caractères.")
        String password

) {}