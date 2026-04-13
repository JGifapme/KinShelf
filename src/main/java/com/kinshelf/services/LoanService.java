package com.kinshelf.services;

import com.kinshelf.dto.loan.*;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.Loan;
import com.kinshelf.entities.User;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.BookRepository;
import com.kinshelf.repositories.LoanRepository;
import com.kinshelf.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    
    public LoanResponseDTO create(LoanCreateDTO dto) {

        //A faire : vérifier que le livre n'est pas déjà dans un pret !

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
    
    public LoanResponseDTO findById(Integer id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prêt introuvable pour l'id : " + id));

        return LoanMapper.toDTO(loan);
    }
    
    public LoanResponseDTO update(Integer id, LoanCreateDTO dto) {

        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Prêt introuvable pour l'id : " + id));

        LoanMapper.updateEntity(loan, dto);

        return LoanMapper.toDTO(loanRepository.save(loan));
    }
    
    public void delete(Integer id) {
        if (!loanRepository.existsById(id)) {
            throw new NotFoundException("Prêt introuvable pour l'id : " + id);
        }
        loanRepository.deleteById(id);
    }
    
    public List<LoanResponseDTO> findByBorrower(Integer userId) {
        return loanRepository.findByBorrowerId(userId)
                .stream()
                .map(LoanMapper::toDTO)
                .toList();
    }
}
