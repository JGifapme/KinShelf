package com.kinshelf.services;

import com.kinshelf.dto.user.UserCreateDTO;
import com.kinshelf.dto.user.UserMapper;
import com.kinshelf.dto.user.UserResponseDTO;
import com.kinshelf.entities.User;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO create(UserCreateDTO dto) {
        String slug = Slugify.toSlug(dto.firstName()+" "+dto.lastName());
        // vérifier que le slug est unique
        while (userRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà.");
        }
        User user = UserMapper.toEntity(dto);
        user.setSlug(slug);
        // a hasher après avoir appris spring security
        User saved = userRepository.save(user);

        return UserMapper.toDTO(saved);
    }
    
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .toList();
    }
    
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable pour l'id : " + id));

        return UserMapper.toDTO(user);
    }
    public UserResponseDTO findBySlug(String slug) {
        User user = userRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable pour cette url."));

        return UserMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO update(Long id, UserCreateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable pour l'id : " + id));

        UserMapper.updateEntity(user, dto);

        return UserMapper.toDTO(userRepository.save(user));
    }
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Utilisateur introuvable pour l'id : " + id);
        }

        userRepository.deleteById(id);
    }
}
