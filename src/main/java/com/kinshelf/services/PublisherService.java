package com.kinshelf.services;

import com.kinshelf.dto.publisher.PublisherCreateDTO;
import com.kinshelf.dto.publisher.PublisherMapper;
import com.kinshelf.dto.publisher.PublisherResponseDTO;
import com.kinshelf.entities.Publisher;
import com.kinshelf.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherResponseDTO create(PublisherCreateDTO dto) {
        Publisher publisher = PublisherMapper.toEntity(dto);
        return PublisherMapper.toDTO(publisherRepository.save(publisher));
    }

    public List<PublisherResponseDTO> findAll() {
        return publisherRepository.findAll()
                .stream()
                .map(PublisherMapper::toDTO)
                .toList();
    }

    public PublisherResponseDTO findById(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Éditeur non trouvé pour l'id : " + id));

        return PublisherMapper.toDTO(publisher);
    }

    public PublisherResponseDTO update(Long id, PublisherCreateDTO dto) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Éditeur non trouvé pour l'id : " + id));

        PublisherMapper.updateEntity(publisher, dto);

        return PublisherMapper.toDTO(publisherRepository.save(publisher));
    }

    public void delete(Long id) {
        if (!publisherRepository.existsById(id)) {
            throw new RuntimeException("Éditeur non trouvé pour l'id : " + id);
        }

        publisherRepository.deleteById(id);
    }
}
