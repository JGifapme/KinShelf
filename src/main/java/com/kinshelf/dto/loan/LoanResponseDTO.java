package com.kinshelf.dto.loan;

import java.time.LocalDate;

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
