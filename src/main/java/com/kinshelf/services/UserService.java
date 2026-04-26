package com.kinshelf.services;

import com.kinshelf.dto.user.UserCreateDTO;
import com.kinshelf.dto.user.UserMapper;
import com.kinshelf.dto.user.UserResponseDTO;
import com.kinshelf.entities.User;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO create(UserCreateDTO dto) {
        User user = UserMapper.toEntity(dto);

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
