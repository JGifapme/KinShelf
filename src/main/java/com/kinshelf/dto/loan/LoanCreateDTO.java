package com.kinshelf.dto.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
/// DTO avec les informations nécessaire pour créer un prêt d'un livre entre 2 utilisateurs.
/// Aussi utilisé pour mettre à jour la date de retour lors de la cloture du prêt.
public record LoanCreateDTO(

        @NotNull(message = "Id manquant pour le livre.")
        Long bookId,

        @NotNull(message = "Id manquant pour le prêteur.")
        Long ownerId, // Id du prêteur

        @NotNull(message = "Id manquant pour le l'emprunteur'.")
        Long borrowerId, // Id de l'emprunteur

        @NotNull(message = "La date ne peut pas être vide.")
        LocalDate loanDate, // Date de début du prêt (Mise à la date du jour)

        LocalDate returnDate //  Mise à NULL lors de la création, Mais comme
        // ce DTO est aussi utilisé lors de l'update, on met la date de retour à ce moment-là
) {}