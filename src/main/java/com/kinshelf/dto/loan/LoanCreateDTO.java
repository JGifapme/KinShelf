package com.kinshelf.dto.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LoanCreateDTO(

        @NotNull
        Integer bookId,

        @NotNull
        Integer ownerId,

        @NotNull
        Integer borrowerId,

        LocalDate loanDate,

        LocalDate returnDate

) {}