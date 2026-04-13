package com.kinshelf.repositories;

import com.kinshelf.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan,Integer> {
    List<Loan> findByBorrowerId(Integer borrowerId);
}
