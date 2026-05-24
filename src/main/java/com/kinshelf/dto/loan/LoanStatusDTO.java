package com.kinshelf.dto.loan;

public record LoanStatusDTO(
        boolean available,
        Long loanId,
        String borrowerUsername
) {}
