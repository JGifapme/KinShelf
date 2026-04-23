package com.kinshelf.controllers;

import com.kinshelf.dto.loan.LoanCreateDTO;
import com.kinshelf.dto.loan.LoanResponseDTO;
import com.kinshelf.services.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    //Controller pour Pret ou dans BookController ? Voir tout les prets de tout le groupe est utile ...
    // mais les autres méthodes plutot dans les tables dédiée soit BookController soit UserController

    @PostMapping
    public ResponseEntity<LoanResponseDTO> create(@Valid @RequestBody LoanCreateDTO dto) {
        return ResponseEntity.ok(loanService.create(dto));
    }
    
    @GetMapping
    public ResponseEntity<List<LoanResponseDTO>> getAll() {
        return ResponseEntity.ok(loanService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(loanService.findById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<LoanResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody LoanCreateDTO dto
    ) {
        return ResponseEntity.ok(loanService.update(id, dto));
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
