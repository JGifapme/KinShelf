package com.kinshelf.dto.publisher;

import com.kinshelf.entities.Publisher;

public class PublisherMapper {

    public static PublisherResponseDTO toDTO(Publisher publisher) {
        if (publisher == null) return null;

        return new PublisherResponseDTO(
                publisher.getId(),
                publisher.getName()
        );
    }

    public static Publisher toEntity(PublisherCreateDTO dto) {
        if (dto == null) return null;

        return Publisher.builder()
                .name(dto.name())
                .build();
    }

    public static void updateEntity(Publisher publisher, PublisherCreateDTO dto) {
        if (publisher == null || dto == null) return;
        publisher.setName(dto.name());
    }
}
