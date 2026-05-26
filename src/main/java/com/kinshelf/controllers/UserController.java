package com.kinshelf.controllers;

import com.kinshelf.dto.user.*;
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
    
//    @PostMapping // On utilise le register pour créer son compte
//    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserCreateDTO dto) {
//        return ResponseEntity.ok(userService.create(dto));
//    }

    /// Pour la page d'admin, une liste des utilisateurs.
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    /// pour aller sur le profil d'un membre
    @GetMapping("/id/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /// pour aller sur le profil d'un membre
    @GetMapping("/{slug}")
    public ResponseEntity<UserResponseDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(userService.findBySlug(slug));
    }

    ///  pour modifier les infos sur le profil de l'utilisateur connecté
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto,
            @AuthenticationPrincipal UserDetailsImplementation userDetails
    ) {
        return ResponseEntity.ok(userService.update(id, dto, userDetails));
    }

    ///  pour la page admin, pour supprimer un utilisateur, faire un soft delete ? pas utilisé dans le front
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    ///  Pour voir son profil
    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMe(@AuthenticationPrincipal UserDetailsImplementation userDetails) {
        return ResponseEntity.ok(userService.findByUsername(userDetails.getUsername()));
    }

    ///  pour voir ses stats
    @GetMapping("/me/stats")
    public ResponseEntity<UserStatsDTO> getMyStats(
            @AuthenticationPrincipal UserDetailsImplementation userDetails) {
        return ResponseEntity.ok(userService.getStats(userDetails.getUserEntity().getId()));
    }
}
