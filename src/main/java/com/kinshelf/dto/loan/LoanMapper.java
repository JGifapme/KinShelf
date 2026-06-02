package com.kinshelf.dto.loan;

import com.kinshelf.entities.Loan;
/// Classe utilitaire pour convertir les objets Loan en DTO et inversément
public class LoanMapper {
    /// Convertit un objet Loan en DTO LoanResponseDTO
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
                loan.getBook().getSlug(),

                loan.getOwner().getId(),
                loan.getOwner().getUsername(),

                loan.getBorrower().getId(),
                loan.getBorrower().getUsername(),
                loan.getBorrower().getSlug(),

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
