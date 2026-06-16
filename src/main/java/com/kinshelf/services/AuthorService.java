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
/// Classe qui reprend la logique autour de l'entitée Author.
/// Fait le pont entre les controleurs et l'AuthorRepository.
public class AuthorService {

    private final AuthorRepository authorRepository;

    /** Crée un nouvel auteur à partir des données fournies.
     * @param dto contient les informations nécessaires à la création de l'auteur.
     * @return un {@link AuthorResponseDTO} représentant l'auteur créé.
     * @throws BadRequestException si le nom est vide ou si l'URL générée
     *                             à partir du nom existe déjà.
     */
    @Transactional
    public AuthorResponseDTO create(AuthorCreateDTO dto) {
        if (dto.name().isEmpty()) {
            throw new BadRequestException("Le nom ne peut être vide.");
        }
        String slug = Slugify.toSlug(dto.name());
        // vérifier que le slug est unique
        if (authorRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà. Vérifiez qu'un auteur avec un nom similaire n'existe pas déjà.");
        }
        Author author = AuthorMapper.toEntity(dto);
        author.setSlug(slug);
        Author saved = authorRepository.save(author);
        return AuthorMapper.toDTO(saved);
    }
    /// Retourne la liste de tous les auteurs
    public List<AuthorResponseDTO> findAll() {
        return authorRepository.findAllByOrderByNameAsc()
                .stream()
                .map(AuthorMapper::toDTO)
                .toList();
    }

    /// Retourne un auteur (nom, id, slug) et la liste de ses livres via son id.
    public AuthorWithBooksDTO findById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("L'auteur introuvable pour l'id : " + id));

        return AuthorMapper.toDTOWithBooks(author);
    }
    /// Retourne un auteur (nom, id, slug) et la liste de ses livres via son slug.
    public @Nullable AuthorWithBooksDTO findBySlug(String slug) {
        Author author = authorRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("L'auteur introuvable pour cette url."));

        return AuthorMapper.toDTOWithBooks(author);
    }
    /// Méthode qui update les information d'un auteur via son id et retourne l'auteur modifié.
    @Transactional
    public AuthorResponseDTO update(Long id, AuthorCreateDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("L'auteur introuvable pour l'id : " + id));

        AuthorMapper.updateEntity(author, dto);

        Author saved = authorRepository.save(author);
        return AuthorMapper.toDTO(saved);
    }
    /// Supprime un auteur dans la base de donnée via son id.
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
