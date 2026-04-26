package com.kinshelf.services;

import com.kinshelf.dto.series.SeriesCreateDTO;
import com.kinshelf.dto.series.SeriesMapper;
import com.kinshelf.dto.series.SeriesResponseDTO;
import com.kinshelf.dto.series.SeriesWithBooksDTO;
import com.kinshelf.entities.Series;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.SeriesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeriesService {

    private final SeriesRepository seriesRepository;

    @Transactional
    public SeriesResponseDTO create(SeriesCreateDTO dto) {
        Series series = SeriesMapper.toEntity(dto);
        return SeriesMapper.toDTO(seriesRepository.save(series));
    }
    
    public List<SeriesResponseDTO> findAll() {
        return seriesRepository.findAll()
                .stream()
                .map(SeriesMapper::toDTO)
                .toList();
    }
    
    public SeriesWithBooksDTO findById(Long id) {
        Series series = seriesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Série introuvable pour l'id : " + id));

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
