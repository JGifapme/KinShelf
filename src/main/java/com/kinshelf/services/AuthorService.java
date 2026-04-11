package com.kinshelf.services;

import com.kinshelf.dto.author.AuthorCreateDTO;
import com.kinshelf.dto.author.AuthorMapper;
import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.entities.Author;
import com.kinshelf.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorResponseDTO create(AuthorCreateDTO dto) {
        Author author = AuthorMapper.toEntity(dto);
        Author saved = authorRepository.save(author);
        return AuthorMapper.toDTO(saved);
    }

    public List<AuthorResponseDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toDTO)
                .toList();
    }

    public AuthorResponseDTO findById(Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("L'auteur introuvable pour l'id : " + id));

        return AuthorMapper.toDTO(author);
    }

    public AuthorResponseDTO update(Integer id, AuthorCreateDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("L'auteur introuvable pour l'id : " + id));

        AuthorMapper.updateEntity(author, dto);

        Author saved = authorRepository.save(author);
        return AuthorMapper.toDTO(saved);
    }

    public void delete(Integer id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("L'auteur introuvable pour l'id : " + id);
        }

        authorRepository.deleteById(id);
    }
}
