package com.kinshelf.dto.category;

import com.kinshelf.entities.Category;

public class CategoryMapper {

    public static CategoryResponseDTO toDTO(Category category) {
        if (category == null) return null;

        return new CategoryResponseDTO(
                category.getId(),
                category.getName()
        );
    }

    public static Category toEntity(CategoryCreateDTO dto) {
        if (dto == null) return null;

        return Category.builder()
                .name(dto.name())
                .build();
    }

    public static void updateEntity(Category category, CategoryCreateDTO dto) {
        if (category == null || dto == null) return;

        category.setName(dto.name());
    }
}