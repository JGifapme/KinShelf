package com.kinshelf.controllers;

import com.kinshelf.dto.bookUser.BookUserCreateDTO;
import com.kinshelf.dto.bookUser.BookUserResponseDTO;
import com.kinshelf.services.BookUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookusers")
@RequiredArgsConstructor
public class BookUserController {

    private final BookUserService bookUserService;
    
    @PostMapping
    public ResponseEntity<BookUserResponseDTO> create(@Valid @RequestBody BookUserCreateDTO dto) {
        return ResponseEntity.ok(bookUserService.create(dto));
    }
    
    @GetMapping
    public ResponseEntity<List<BookUserResponseDTO>> getAll(@RequestParam(required = false) Long userId) {
        List<BookUserResponseDTO> listBU;
        if (userId != null) {
            listBU = bookUserService.findByUser(userId);
        }
        else {
            listBU = bookUserService.findAll();
        }
        return ResponseEntity.ok(listBU);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BookUserResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookUserService.findById(id));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BookUserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody BookUserCreateDTO dto
    ) {
        return ResponseEntity.ok(bookUserService.update(id, dto));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookUserService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
