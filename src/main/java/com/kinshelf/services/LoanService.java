package com.kinshelf.services;

import com.kinshelf.dto.loan.*;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.Loan;
import com.kinshelf.entities.User;
import com.kinshelf.entities.UserDetailsImplementation;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.BookRepository;
import com.kinshelf.repositories.LoanRepository;
import com.kinshelf.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    
    public LoanResponseDTO create(Long ownerId, Long borrowerId, Long bookId) {

        LoanCreateDTO dto = new LoanCreateDTO(bookId, ownerId, borrowerId, LocalDate.now(), null);

        Book book = bookRepository.findById(dto.bookId())
                .orElseThrow(() -> new NotFoundException("Livre introuvbable"));

        User owner = userRepository.findById(dto.ownerId())
                .orElseThrow(() -> new NotFoundException("Prêteur introuvable"));

        User borrower = userRepository.findById(dto.borrowerId())
                .orElseThrow(() -> new NotFoundException("Emprunteur introuvable"));

        // évite de s'auto pr^ter
        if (owner.getId().equals(borrower.getId())) {
            throw new RuntimeException("Prêteur et emprunteur identique.");
        }
        //A faire : vérifier que le livre n'est pas déjà dans un pret !
        if (loanRepository.existsByOwnerIdAndBookIdAndReturnDateNull(ownerId, bookId)) {
            throw new BadRequestException("Le livre est déjà en prêt.");
        }

        Loan loan = Loan.builder()
                .book(book)
                .owner(owner)
                .borrower(borrower)
                .loanDate(dto.loanDate() != null ? dto.loanDate() : LocalDate.now())
                .returnDate(dto.returnDate())
                .build();

        return LoanMapper.toDTO(loanRepository.save(loan));
    }
    
    public List<LoanResponseDTO> findAll() {
        return loanRepository.findAll()
                .stream()
                .map(LoanMapper::toDTO)
                .toList();
    }
    
    public LoanResponseDTO findById(Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prêt introuvable pour l'id : " + id));

        return LoanMapper.toDTO(loan);
    }
    
    public LoanResponseDTO update(Long id, UserDetailsImplementation userDetails) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prêt introuvable pour l'id : " + id));
        if (loan.getOwner().getId() != userDetails.getUserEntity().getId()) {
            throw new BadRequestException("Mauvais prêteur.");
        }
        LoanCreateDTO dto = new LoanCreateDTO(
                loan.getBook().getId(),
                id,
                loan.getBorrower().getId(),
                loan.getLoanDate(),
                LocalDate.now()
        );

        LoanMapper.updateEntity(loan, dto);

        return LoanMapper.toDTO(loanRepository.save(loan));
    }
    
    public void delete(Long id) {
        if (!loanRepository.existsById(id)) {
            throw new NotFoundException("Prêt introuvable pour l'id : " + id);
        }
        loanRepository.deleteById(id);
    }
    
    public List<LoanResponseDTO> findByBorrower(Long userId) {
        return loanRepository.findByBorrowerId(userId)
                .stream()
                .map(LoanMapper::toDTO)
                .toList();
    }

    public LoanStatusDTO getLoanStatusForBook(Long bookId, Long id) {
        Loan loan = loanRepository.findByOwnerIdAndBookIdAndReturnDateNull(id, bookId);
        boolean available = true;
        Long loanId = null;
        String borrowerName = null;
        if (loan != null) {
                available = false;
                loanId = loan.getId();
                borrowerName = loan.getBorrower().getUsername();
        }

        return new LoanStatusDTO(
                available,
                loanId,
                borrowerName
        );
    }
}
