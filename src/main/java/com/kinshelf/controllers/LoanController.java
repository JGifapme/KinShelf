package com.kinshelf.controllers;

import com.kinshelf.dto.loan.LoanCreateDTO;
import com.kinshelf.dto.loan.LoanResponseDTO;
import com.kinshelf.dto.loan.LoanStatusDTO;
import com.kinshelf.entities.UserDetailsImplementation;
import com.kinshelf.filters.LoanFilter;
import com.kinshelf.services.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
/// Controleur qui gère les prêts de livres entre les utilisateurs.
/// POST pour créer un prêt, GET pour la liste des prêts d'un utilisateur et PATCH pour cloturer un prêt
@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class LoanController {

    private final LoanService loanService;


    /// Création d'un prêt de l'utilisateur connecté (owner) vers un autre utilisateur (borrower) par son id d'un livre par son id
    @PostMapping("/{bookId}/to/{borrowerId}")
    public ResponseEntity<LoanResponseDTO> createLoan(
            @PathVariable Long bookId,
            @PathVariable Long borrowerId,
            // seulement le permettre pour l'utilisateur identifié, récupérer son id via spring security :
            @AuthenticationPrincipal UserDetailsImplementation userDetails) {

        return ResponseEntity.ok(loanService.create(userDetails.getUserEntity().getId(), borrowerId, bookId));
    }

    /// Rentourne les prêts de l'utilisateur avec un filtre : prété, emprunté et historique en fonction de ce qu'on veut voir
    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getMyLoans(
            @RequestParam LoanFilter filter,
            @AuthenticationPrincipal UserDetailsImplementation userDetails) {
        return ResponseEntity.ok(loanService.getMyLoans(filter, userDetails.getUserEntity().getId()));
    }

    ///  Retourne si le livre est possédé par l'utilisateur, si il est prêté ou non et si oui à qui
    @GetMapping("/{bookId}/status")
    public ResponseEntity<LoanStatusDTO> getLoanStatusForBook(
            @PathVariable Long bookId,
            @AuthenticationPrincipal UserDetailsImplementation userDetails) {

        return ResponseEntity.ok(loanService.getLoanStatusForBook(bookId, userDetails.getUserEntity().getId()));
    }
    /// Met à jour la date de retour d'un prêt à la date d'aujourd'hui, cloturant ainsi le prêt
    @PatchMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> update(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImplementation userDetails
    ) {
        return ResponseEntity.ok(loanService.update(id, userDetails));
    }
}
