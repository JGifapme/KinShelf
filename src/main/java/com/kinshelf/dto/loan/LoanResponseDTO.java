package com.kinshelf.dto.loan;

import java.time.LocalDate;

public record LoanResponseDTO(

        Long id,
        Long bookId,
        String bookTitle,

        Long ownerId,
        String ownerName,

        Long borrowerId,
        String borrowerName,

        LocalDate loanDate,
        LocalDate returnDate

) {}
