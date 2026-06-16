package com.kinshelf.services;

import com.kinshelf.dto.series.SeriesCreateDTO;
import com.kinshelf.dto.series.SeriesMapper;
import com.kinshelf.dto.series.SeriesResponseDTO;
import com.kinshelf.dto.series.SeriesWithBooksDTO;
import com.kinshelf.entities.Series;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.SeriesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    @Transactional
    public SeriesResponseDTO create(SeriesCreateDTO dto) {
        String slug = Slugify.toSlug(dto.name());
        // vérifier que le slug est unique
        while (seriesRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà. Vérifier qu'une série avec un nom similaire n'existe pas déjà.");
        }
        Series series = SeriesMapper.toEntity(dto);
        series.setSlug(slug);
        return SeriesMapper.toDTO(seriesRepository.save(series));
    }
    
    public List<SeriesResponseDTO> findAll() {
        return seriesRepository.findAllByOrderByNameAsc()
                .stream()
                .map(SeriesMapper::toDTO)
                .toList();
    }
    
    public SeriesWithBooksDTO findById(Long id) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Série introuvable pour l'id : " + id));

        return SeriesMapper.toDTOSeriesWithBooks(series);
    }
    public SeriesWithBooksDTO findBySlug(String slug) {
        Series series = seriesRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Série introuvable pour cette url."));
        return SeriesMapper.toDTOSeriesWithBooks(series);
    }

    @Transactional
    public SeriesResponseDTO update(Long id, SeriesCreateDTO dto) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Série introuvable pour l'id : " + id));

        SeriesMapper.updateEntity(series, dto);

        return SeriesMapper.toDTO(seriesRepository.save(series));
    }
    @Transactional
    public void delete(Long id) {
        if (!seriesRepository.existsById(id)) {
            throw new NotFoundException("Série introuvable pour l'id : " + id);
        }

        seriesRepository.deleteById(id);
    }
}
