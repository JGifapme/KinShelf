package com.kinshelf.dto.loan;

import com.kinshelf.entities.Loan;

public class LoanMapper {

    public static LoanResponseDTO toDTO(Loan loan) {
        if (loan == null) {
            return null;
        }
        boolean available = true;
        if (loan.getReturnDate() == null) {
            available = false;
        }

        return new LoanResponseDTO(
                loan.getId(),

                loan.getBook().getId(),
                loan.getBook().getTitle(),

                loan.getOwner().getId(),
                loan.getOwner().getUsername(),

                loan.getBorrower().getId(),
                loan.getBorrower().getUsername(),

                loan.getLoanDate(),
                loan.getReturnDate(),
                available

        );
    }

    public static LoanCreateDTO toCreateDTO(Loan loan) {
        if (loan == null) {
            return null;
        }

        return new LoanCreateDTO(
                      loan.getBook().getId(),
                loan.getOwner().getId(),
                loan.getBorrower().getId(),
                loan.getLoanDate(),
                loan.getReturnDate()
        );
    }

    public static void updateEntity(Loan loan, LoanCreateDTO dto) {
        if (loan == null || dto == null) {
            return;
        }
        //la seule infos qui peut changer dans un prêt :
        if (dto.returnDate() != null) {
            loan.setReturnDate(dto.returnDate());
        }
    }
}
