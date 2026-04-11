package com.kinshelf.services;

import com.kinshelf.dto.loan.LoanCreateDTO;
import com.kinshelf.entities.Book;
import com.kinshelf.entities.Loan;
import com.kinshelf.entities.User;
import com.kinshelf.repositories.BookRepository;
import com.kinshelf.repositories.LoanRepository;
import com.kinshelf.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    public Loan create(LoanCreateDTO dto) {

        Book book = bookRepository.findById(dto.bookId()).orElseThrow();
        User owner = userRepository.findById(dto.ownerId()).orElseThrow();
        User borrower = userRepository.findById(dto.borrowerId()).orElseThrow();

        Loan loan = Loan.builder()
                .book(book)
                .owner(owner)
                .borrower(borrower)
                .loanDate(dto.loanDate())
                .returnDate(dto.returnDate())
                .build();

        return loanRepository.save(loan);
    }
}
