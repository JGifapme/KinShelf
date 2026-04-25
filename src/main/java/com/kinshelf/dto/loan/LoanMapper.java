package com.kinshelf.dto.loan;

import com.kinshelf.entities.Loan;

public class LoanMapper {

    public static LoanResponseDTO toDTO(Loan loan) {
        if (loan == null) {
            return null;
        }

        return new LoanResponseDTO(
                loan.getId(),

                loan.getBook().getId(),
                loan.getBook().getTitle(),

                loan.getOwner().getId(),
                loan.getOwner().getFirstName() + " " + loan.getOwner().getLastName(),

                loan.getBorrower().getId(),
                loan.getBorrower().getFirstName() + " " + loan.getBorrower().getLastName(),

                loan.getLoanDate(),
                loan.getReturnDate()
        );
    }

    public static void updateEntity(Loan loan, LoanCreateDTO dto) {
        if (loan == null || dto == null) {
            return;
        }

        loan.setLoanDate(dto.loanDate());
        loan.setReturnDate(dto.returnDate());
    }
}
