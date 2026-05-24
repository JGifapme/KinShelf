package com.kinshelf.controllers;

import com.kinshelf.dto.loan.LoanCreateDTO;
import com.kinshelf.dto.loan.LoanResponseDTO;
import com.kinshelf.dto.loan.LoanStatusDTO;
import com.kinshelf.entities.UserDetailsImplementation;
import com.kinshelf.services.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class LoanController {

    private final LoanService loanService;

    //Controller pour Pret ou dans BookController ? Voir tout les prets de tout le groupe est utile ...
    // mais les autres méthodes plutot dans les tables dédiée soit BookController soit UserController

    /// Gestion des prêts (loans).
    @PostMapping("/{bookId}/to/{borrowerId}")
    public ResponseEntity<LoanResponseDTO> createLoan(
            @PathVariable Long bookId,
            @PathVariable Long borrowerId,
            // seulement le permettre pour l'utilisateur identifié, récupérer son id via spring security :
            @AuthenticationPrincipal UserDetailsImplementation userDetails) {

        return ResponseEntity.ok(loanService.create(userDetails.getUserEntity().getId(), borrowerId, bookId));
    }
    
    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAll() {
        return ResponseEntity.ok(loanService.findAll());
    }

    @GetMapping("/{bookId}/status")
    public ResponseEntity<LoanStatusDTO> getLoanStatusForBook(
            @PathVariable Long bookId,
            @AuthenticationPrincipal UserDetailsImplementation userDetails) {

        return ResponseEntity.ok(loanService.getLoanStatusForBook(bookId, userDetails.getUserEntity().getId()));
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> update(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImplementation userDetails
    ) {
        return ResponseEntity.ok(loanService.update(id, userDetails));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        loanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/borrower/{userId}")
    public ResponseEntity<List<LoanResponseDTO>> getByBorrower(@PathVariable Long userId) {
        return ResponseEntity.ok(loanService.findByBorrower(userId));
    }
}
