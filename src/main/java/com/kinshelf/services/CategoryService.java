package com.kinshelf.services;

import com.kinshelf.dto.category.CategoryCreateDTO;
import com.kinshelf.dto.category.CategoryMapper;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.dto.category.CategoryWithBooksDTO;
import com.kinshelf.entities.Category;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
/// Classe qui reprend la logique autour de l'entitée Category.
/// Fait le pont entre les controleurs et le CategoryRepository.
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponseDTO create(CategoryCreateDTO dto) {
        String slug = Slugify.toSlug(dto.name());
        // vérifier que le slug est unique
        while (categoryRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà. Vérifiez qu'une catégorie d'ouvrage avec un nom similaire n'existe pas déjà.");
        }
        Category category = CategoryMapper.toEntity(dto);
        category.setSlug(slug);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    public CategoryWithBooksDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Catégorie introuvable pour l'id : " + id));

        return CategoryMapper.toDTOCatWithBooks(category);
    }
    public CategoryResponseDTO findBySlug(String slug) {
        Category category = categoryRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Catégorie introuvable pour cette url."));

        return CategoryMapper.toDTO(category);
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryCreateDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Catégorie introuvable pour l'id : " + id));

        CategoryMapper.updateEntity(category, dto);

        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new NotFoundException("Catégorie introuvable pour l'id : " + id);
        }

        categoryRepository.deleteById(id);
    }
}
