package com.kinshelf.repositories;

import com.kinshelf.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    List<Loan> findByBorrowerId(Long borrowerId);
    boolean existsByOwnerIdAndBookIdAndReturnDateNull(Long ownerId, Long bookId);
    Loan findByOwnerIdAndBookIdAndReturnDateNull(Long ownerId, Long bookId);
    List<Loan> findByOwnerId(Long id);
    List<Loan> findByOwnerIdAndReturnDateNull(Long bookId);
    List<Loan> findByBorrowerIdAndReturnDateNull(Long bookId);
    List<Loan> findByOwnerIdAndReturnDateNotNull(Long bookId);
    int countByOwnerId(Long userId);
}
