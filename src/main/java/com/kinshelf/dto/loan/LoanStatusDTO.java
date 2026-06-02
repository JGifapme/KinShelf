package com.kinshelf.dto.loan;
/// DTO qui renvoie le status d'un livre : s'il est disponible ou non, et sinon, à qui il est prêté
public record LoanStatusDTO(
        boolean available,
        Long loanId,
        String borrowerUsername
) {}
