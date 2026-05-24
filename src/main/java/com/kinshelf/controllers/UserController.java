package com.kinshelf.controllers;

import com.kinshelf.dto.user.UserCreateDTO;
import com.kinshelf.dto.user.UserProfileDTO;
import com.kinshelf.dto.user.UserResponseDTO;
import com.kinshelf.dto.user.UserUpdateDTO;
import com.kinshelf.entities.UserDetailsImplementation;
import com.kinshelf.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // seul les utilisateurs authentifié/connecté peuvent utiliser cet endpoints
public class UserController {

    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO dto) {
        return ResponseEntity.ok(userService.create(dto));
    }
    
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<UserResponseDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(userService.findBySlug(slug));
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto,
            @AuthenticationPrincipal UserDetailsImplementation userDetails
    ) {
        return ResponseEntity.ok(userService.update(id, dto, userDetails));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMe(@AuthenticationPrincipal UserDetailsImplementation userDetails) {
        return ResponseEntity.ok(userService.findByUsername(userDetails.getUsername()));
    }

    //GET /{userId}/collection : voir tout les livres possédé par tel membre
}
