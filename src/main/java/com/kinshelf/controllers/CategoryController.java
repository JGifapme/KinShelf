package com.kinshelf.controllers;

import com.kinshelf.dto.category.CategoryCreateDTO;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.dto.category.CategoryWithBooksDTO;
import com.kinshelf.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// Controleur qui permet de créer, récupérer, mettre à jour et supprimer les informations sur une catégorie
/// (Roman, BD, manga). Dans les faits seuls les endpoints GET sont utilisés, les catégories étant déjà définies.
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")// seuls les utilisateurs authentifiés/connectés peuvent utiliser cet endpoint
public class CategoryController {

    private final CategoryService categoryService;

    /// Endpoint qui permet de créer une catégorie, non utilisée dans le front
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    /// Endpoint qui permet de récupérer la liste de toutes les catégories dans la base de donnée
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    /// Endpoint qui permet de récupérer les informations d'une catégorie et la liste des livres associés grâce à son id
    @GetMapping("/id/{id}")
    public ResponseEntity<CategoryWithBooksDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
    /// Endpoint qui permet de récupérer les informations d'une catégorie et la liste des livres associés grâce à son slug
    @GetMapping("/{slug}")
    public ResponseEntity<CategoryResponseDTO> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(categoryService.findBySlug(slug));
    }

    /// Endpoint qui permet de mettre à jour les informations d'une catégorie (admin seulement), non utilisée dans le front
    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<CategoryResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryCreateDTO dto
    ) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    /// Endpoint qui permet de supprimer une catégorie (admin seulement), non utilisée dans le front
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") // seulement les admins sont autorisé à utiliser cet endpoint
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
        //vérifier que ça ne supprime pas les livres associés
    }
}
