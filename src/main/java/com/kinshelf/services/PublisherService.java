package com.kinshelf.services;

import com.kinshelf.dto.publisher.PublisherCreateDTO;
import com.kinshelf.dto.publisher.PublisherMapper;
import com.kinshelf.dto.publisher.PublisherResponseDTO;
import com.kinshelf.dto.publisher.PublisherWithBooksDTO;
import com.kinshelf.entities.Publisher;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.PublisherRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Transactional
    public PublisherResponseDTO create(PublisherCreateDTO dto) {
        String slug = Slugify.toSlug(dto.name());
        // vérifier que le slug est unique
        while (publisherRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà.");
        }
        Publisher publisher = PublisherMapper.toEntity(dto);
        publisher.setSlug(slug);
        return PublisherMapper.toDTO(publisherRepository.save(publisher));
    }

    public List<PublisherResponseDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(PublisherMapper::toDTO)
                .toList();
    }

    public PublisherWithBooksDTO findById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Éditeur non trouvé pour l'id : " + id));
        return PublisherMapper.toDTOPublisherWithBooks(publisher);
    }
    public PublisherWithBooksDTO findBySlug(String slug) {
        Publisher publisher = publisherRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Éditeur non trouvé pour cette url."));
        return PublisherMapper.toDTOPublisherWithBooks(publisher);
    }

    @Transactional
    public PublisherResponseDTO update(Long id, PublisherCreateDTO dto) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Éditeur non trouvé pour l'id : " + id));

        PublisherMapper.updateEntity(publisher, dto);

        return PublisherMapper.toDTO(publisherRepository.save(publisher));
    }

    @Transactional
    public void delete(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new NotFoundException("Éditeur non trouvé pour l'id : " + id);
        }
        publisherRepository.deleteById(id);
    }
}
