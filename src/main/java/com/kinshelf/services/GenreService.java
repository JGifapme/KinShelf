package com.kinshelf.services;

import com.kinshelf.dto.genre.GenreCreateDTO;
import com.kinshelf.dto.genre.GenreMapper;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.dto.genre.GenreWithBooksDTO;
import com.kinshelf.entities.Genre;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    @Transactional
    public GenreResponseDTO create(GenreCreateDTO dto) {
        String slug = Slugify.toSlug(dto.name());
        // vérifier que le slug est unique
        while (genreRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà.");
        }
        Genre genre = GenreMapper.toEntity(dto);
        genre.setSlug(slug);
        return GenreMapper.toDTO(genreRepository.save(genre));
    }
    
    public List<GenreResponseDTO> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(GenreMapper::toDTO)
                .toList();
    }
    
    public GenreWithBooksDTO findById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre introuvable pour l'id : " + id));

        return GenreMapper.toDTOGenreWithBooks(genre);
    }
    public @Nullable GenreWithBooksDTO findBySlug(String slug) {
        Genre genre = genreRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Genre introuvable pour cette url."));

        return GenreMapper.toDTOGenreWithBooks(genre);
    }

    @Transactional
    public GenreResponseDTO update(Long id, GenreCreateDTO dto) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre introuvable pour l'id : " + id));

        GenreMapper.updateEntity(genre, dto);

        return GenreMapper.toDTO(genreRepository.save(genre));
    }
    @Transactional
    public void delete(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new NotFoundException("Genre introuvable pour l'id : " + id);
        }

        genreRepository.deleteById(id);
    }
}
