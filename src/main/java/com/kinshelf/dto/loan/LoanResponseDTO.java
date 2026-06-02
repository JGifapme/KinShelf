package com.kinshelf.dto.loan;

import java.time.LocalDate;
/// Dto qui retourne toutes les infos liée à un prêt : titre du livre, nom du prêteur et de l'emprunteur, ...
public record LoanResponseDTO(
        Long id,
        Long bookId,
        String bookTitle,
        String bookSlug,

        Long ownerId,
        String ownerName,

        Long borrowerId,
        String borrowerName,
        String borrowerSlug,

        LocalDate loanDate,
        LocalDate returnDate
) {}
