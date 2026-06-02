package com.kinshelf.dto.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
/// DTO avec les informations nécessaire pour créer un prêt d'un livre entre 2 utilisateurs.
/// Aussi utilisé pour mettre à jour la date de retour lors de la cloture du prêt.
public record LoanCreateDTO(

        @NotNull
        Long bookId,

        @NotNull
        Long ownerId, // Id du prêteur

        @NotNull
        Long borrowerId, // Id de l'emprunteur

        @NotNull
        LocalDate loanDate, // Date de début du prêt (Mise à la date du jour)

        LocalDate returnDate //  Mise à NULL lors de la création, Mais comme
        // ce DTO est aussi utilisé lors de l'update, on met la date de retour à ce moment-là
) {}