package com.kinshelf.dto.loan;

import java.time.LocalDate;

public record LoanResponseDTO(

        Integer id,
        Integer bookId,
        String bookTitle,

        Integer ownerId,
        String ownerName,

        Integer borrowerId,
        String borrowerName,

        LocalDate loanDate,
        LocalDate returnDate

) {}
