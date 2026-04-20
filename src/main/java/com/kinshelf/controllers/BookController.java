package com.kinshelf.controllers;

import com.kinshelf.dto.book.BookCreateDTO;
import com.kinshelf.dto.book.BookResponseDTO;
import com.kinshelf.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    
    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookCreateDTO dto) {
        return ResponseEntity.ok(bookService.create(dto));
    }
    
    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BookCreateDTO dto
    ) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
