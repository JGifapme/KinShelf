package com.kinshelf.services;

import com.kinshelf.dto.author.AuthorCreateDTO;
import com.kinshelf.dto.author.AuthorMapper;
import com.kinshelf.dto.author.AuthorResponseDTO;
import com.kinshelf.dto.author.AuthorWithBooksDTO;
import com.kinshelf.entities.Author;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.AuthorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional
    public AuthorResponseDTO create(AuthorCreateDTO dto) {
        if (dto.name().isEmpty()) {
            throw new BadRequestException("Le nom ne peut être vide.");
        }

        String slug = Slugify.toSlug(dto.name());
        // vérifier que le slug est unique
        while (authorRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà. Vérifiez qu'un auteur avec un nom similaire n'existe pas déjà.");
        }
        Author author = AuthorMapper.toEntity(dto);
        author.setSlug(slug);
        Author saved = authorRepository.save(author);
        return AuthorMapper.toDTO(saved);
    }

    public List<AuthorResponseDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(AuthorMapper::toDTO)
                .toList();
    }

    public AuthorWithBooksDTO findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("L'auteur introuvable pour l'id : " + id));

        return AuthorMapper.toDTOWithBooks(author);
    }

    public @Nullable AuthorWithBooksDTO findBySlug(String slug) {
        Author author = authorRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("L'auteur introuvable pour cette url."));

        return AuthorMapper.toDTOWithBooks(author);
    }

    @Transactional
    public AuthorResponseDTO update(Long id, AuthorCreateDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("L'auteur introuvable pour l'id : " + id));

        AuthorMapper.updateEntity(author, dto);

        Author saved = authorRepository.save(author);
        return AuthorMapper.toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("L'auteur introuvable pour l'id : " + id));
        // avant de supprimer l'auteur on vérifie qu'il n'aie plus de livre liés :
        if (!author.getBookAuthors().isEmpty()) {
            throw new BadRequestException("Impossible de supprimer l'auteur : il est encore lié à "
                    + author.getBookAuthors().size() + " livre(s).");
        }
        //si il n'y a plus de livres liés on supprime
        authorRepository.deleteById(id);
    }
}
