package com.kinshelf.services;

import com.kinshelf.dto.user.*;
import com.kinshelf.entities.User;
import com.kinshelf.entities.UserDetailsImplementation;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.ForbiddenException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.BookUserRepository;
import com.kinshelf.repositories.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BookUserRepository bookUserRepository;

    @Transactional
    public UserResponseDTO create(UserCreateDTO dto) {
        String slug = Slugify.toSlug(dto.username());
        // vérifier que le slug, le username et l'email est unique
        if (userRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà. Vérifier que vous ne possédez pas déjà un compte.");
        }
        if (userRepository.existsByUsername(dto.username())) {
            throw new BadRequestException("Ce nom d'utilisateur existe déjà. Vérifier que vous ne possédez pas déjà un compte.");
        }
        if (userRepository.existsByEmail(dto.email())) {
            throw new BadRequestException("Cette adresse email est déjà utilisée. Vérifier que vous ne possédez pas déjà un compte.");
        }
        User user = userMapper.toEntity(dto);
        user.setSlug(slug);
        // a hasher après avoir appris spring security
        User saved = userRepository.save(user);

        return userMapper.toDTO(saved);
    }
    
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }
    
    public UserResponseDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable pour l'id : " + id));

        return userMapper.toDTO(user);
    }
    public UserResponseDTO findBySlug(String slug) {
        User user = userRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable pour cette url."));

        return userMapper.toDTO(user);
    }

    @Transactional
    public UserResponseDTO update(Long id, UserUpdateDTO dto, UserDetailsImplementation userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable pour l'id : " + id));
        if (!user.getId().equals(userDetails.getUserEntity().getId())) {
            throw new ForbiddenException("Vous ne pouvez pas modifier ce profil.");
        }
        userMapper.updateEntity(user, dto);

        return userMapper.toDTO(userRepository.save(user));
    }
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Utilisateur introuvable pour l'id : " + id);
        }

        userRepository.deleteById(id);
    }

    /// méthode utilisée par spring security pour récupérer le usernmae de l'utilisateur
    @Override
    public UserDetailsImplementation loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur introuvable : " + username);
        }
        return new UserDetailsImplementation(user);
    }

    public UserProfileDTO findByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        int age = Period.between(user.getDateOfBirth(),LocalDate.now()).getYears();
        int nbrBooks = bookUserRepository.countByUserIdAndIsOwnTrue(user.getId());
        UserProfileDTO upd = new UserProfileDTO(
                user.getId(),
                user.getUsername(),
                user.getSlug(),
                user.getEmail(),
                user.getDateOfBirth(),
                age,
                nbrBooks
        );
        return upd;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
