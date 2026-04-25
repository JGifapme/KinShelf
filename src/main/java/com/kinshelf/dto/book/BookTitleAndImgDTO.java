package com.kinshelf.dto.book;

public record BookTitleAndImgDTO(
        Long id,
        String title,
        String coverUrl
) {}
