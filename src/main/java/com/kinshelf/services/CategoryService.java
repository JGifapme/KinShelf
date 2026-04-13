package com.kinshelf.services;

import com.kinshelf.dto.category.CategoryCreateDTO;
import com.kinshelf.dto.category.CategoryMapper;
import com.kinshelf.dto.category.CategoryResponseDTO;
import com.kinshelf.entities.Category;
import com.kinshelf.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponseDTO create(CategoryCreateDTO dto) {
        Category category = CategoryMapper.toEntity(dto);
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDTO)
                .toList();
    }

    public CategoryResponseDTO findById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable pour l'id : " + id));

        return CategoryMapper.toDTO(category);
    }

    public CategoryResponseDTO update(Integer id, CategoryCreateDTO dto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable pour l'id : " + id));

        CategoryMapper.updateEntity(category, dto);

        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    public void delete(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Catégorie introuvable pour l'id : " + id);
        }

        categoryRepository.deleteById(id);
    }
}
