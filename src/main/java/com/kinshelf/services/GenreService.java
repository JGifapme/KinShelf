package com.kinshelf.services;

import com.kinshelf.dto.genre.GenreCreateDTO;
import com.kinshelf.dto.genre.GenreMapper;
import com.kinshelf.dto.genre.GenreResponseDTO;
import com.kinshelf.dto.genre.GenreWithBooksDTO;
import com.kinshelf.entities.Genre;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    
    public GenreResponseDTO create(GenreCreateDTO dto) {
        Genre genre = GenreMapper.toEntity(dto);
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
    
    public GenreResponseDTO update(Long id, GenreCreateDTO dto) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre introuvable pour l'id : " + id));

        GenreMapper.updateEntity(genre, dto);

        return GenreMapper.toDTO(genreRepository.save(genre));
    }
    
    public void delete(Long id) {
        if (!genreRepository.existsById(id)) {
            throw new NotFoundException("Genre introuvable pour l'id : " + id);
        }

        genreRepository.deleteById(id);
    }
}
