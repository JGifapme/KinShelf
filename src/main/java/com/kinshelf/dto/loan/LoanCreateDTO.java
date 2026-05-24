package com.kinshelf.dto.loan;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LoanCreateDTO(

        @NotNull
        Long bookId,

        @NotNull
        Long ownerId,

        @NotNull
        Long borrowerId,

        @NotNull
        LocalDate loanDate,

        LocalDate returnDate
) {}