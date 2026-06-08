package com.kinshelf.services;

import com.kinshelf.dto.book.*;
import com.kinshelf.entities.*;
import com.kinshelf.exceptions.BadRequestException;
import com.kinshelf.exceptions.NotFoundException;
import com.kinshelf.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
/// Classe qui reprend la logique autour de l'entitée Book.
/// Fait le pont entre les controleurs et le BookRepository.
public class BookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final SeriesRepository seriesRepository;
    private final GenreRepository genreRepository;
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;
    private final BookMapper bookMapper;

    /**
     * Crée un nouveau livre à partir des données fournies.
     * Cette méthode vérifie l'unicité de l'ISBN, génère un slug unique
     * à partir du titre du livre puis enregistre l'entité en base de données.
     * @throws BadRequestException si l'ISBN existe déjà ou si le slug généré
     *                             est déjà utilisé.
     */
    @Transactional
    public BookResponseDTO create(BookCreateDTO dto) {
        // vérifier que l'isbn est unique
        String isbn = dto.isbn();
        if (bookRepository.existsByIsbn(isbn)){
            throw new BadRequestException("Cet isbn existe déjà, veuillez vérifier que le livre n'existe pas déjà sur KinShelf.");
        }
        // Créer un slug unique :
        String slug = Slugify.toSlug(dto.title());
        Integer number = 1;
        slug = createSlug(slug,number);
        //théoriquement on a créé un slug unique, mais au cas ou, pour renvoyer une erreur propre :
        if (bookRepository.existsBySlug(slug)) {
            throw new BadRequestException("L'url associée existe déjà. Vérifiez qu'un livre avec un nom similaire n'existe pas déjà.");
        }
        Book book = new Book();
        book.setSlug(slug);
        bookMapper.updateEntityFromDTO(book, dto);

        return bookMapper.toDTO(bookRepository.save(book));
    }

    /**
     * Récupère une page de livres triés par titre.
     * @param pageable paramètres de pagination.
     * @return une page de {@link BookTitleAndImgDTO}.
     */
    public Page<BookTitleAndImgDTO> findAll(Pageable pageable) {
        return bookRepository.findAllByOrderByTitleAsc(pageable).map(bookMapper::toDTOTitleAndImg);
    }

    /**
     * Recherche des livres selon différents critères de filtrage,
     * de tri et de pagination.
     * <p>
     * Les filtres sont optionnels. Lorsqu'un paramètre est vide ou null,
     * il n'est pas pris en compte dans la recherche.
     * </p>
     * @param search         texte recherché dans les livres.
     * @param genreSlug      slug du genre à filtrer.
     * @param userSlug       slug de l'utilisateur associé aux livres.
     * @param categorySlug   slug de la catégorie à filtrer.
     * @param publisherSlug  slug de l'éditeur à filtrer.
     * @param userStatus     statut utilisateur du livre
     *                       ("readtrue", "readfalse", "interested").
     * @param page           numéro de page demandé.
     * @param size           nombre d'éléments par page.
     * @param sortBy         mode de tri ("a-z", "z-a", "oldest", "newest").
     * @param userId         identifiant de l'utilisateur connecté.
     * @return une page de {@link BookTitleAndImgDTO} correspondant aux critères.
     */
    public Page<BookTitleAndImgDTO> findAll(String search, String genreSlug, String userSlug, String categorySlug, String publisherSlug, String userStatus, int page, int size, String sortBy, Long userId) {
        Sort sort = switch (sortBy) {
            case "a-z" -> Sort.by("title").ascending();
            case "z-a" -> Sort.by("title").descending();
            case "oldest" -> Sort.by("id").ascending();
            case "newest" -> Sort.by("id").descending();
            default -> Sort.by("title").ascending(); // a-z
        };
        Pageable pageable = PageRequest.of(page, size, sort);
        // On vérifie que les paramètre ne soit pas vide "" ce qui pourrait provoquer des erreurs, on préfère renvoyer null
        String searchN = search;
        if (search == null || search.trim().isEmpty()) {
            searchN = null;
        }
        String genreSlugN = genreSlug;
        if (genreSlug == null || genreSlug.trim().isEmpty()) {
            genreSlugN = null;
        }
        String allUsers = null;
        if (Objects.equals(userSlug, "all-users")) {
            userSlug = null;
            allUsers = "1";
        }
        String userSlugN = userSlug;
        if (userSlug == null || userSlug.trim().isEmpty()) {
            userSlugN = null;
        }
        String categorySlugN = categorySlug;
        if (categorySlug == null || categorySlug.trim().isEmpty()) {
            categorySlugN = null;
        }
        String publisherSlugN = publisherSlug;
        if (publisherSlug == null || publisherSlug.trim().isEmpty()) {
            publisherSlugN = null;
        }
        Boolean userIsRead = null;
        Boolean userIsInterested = null;
        if (userStatus != null) {
            if(userStatus.equalsIgnoreCase("readtrue")){
                userIsRead = true;
            }
            else if(userStatus.equalsIgnoreCase("readfalse")){
                userIsRead = false;
            }
            else if(userStatus.equalsIgnoreCase("interested")){
                userIsInterested = true;
            }
        }
        return bookRepository.findBookSearch(searchN, genreSlugN, userSlugN, categorySlugN, allUsers, publisherSlugN, userId, userIsRead, userIsInterested ,pageable)
                .map(bookMapper::toDTOTitleAndImg);
    }

    /**
     * Recherche un livre par son identifiant.
     * @throws NotFoundException si le livre n'existe pas.
     */
    public BookWithUsersInputDTO findById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour l'id : " + id));
        return bookMapper.toDTOWithUsersInput(book);
    }

    /**
     * Recherche un livre par son slug.
     * @throws NotFoundException si le livre n'existe pas.
     */
    public BookWithUsersInputDTO findBySlug(String slug) {
        Book book = bookRepository.findBySlug(slug)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour cette url."));

        return bookMapper.toDTOWithUsersInput(book);
    }
    public boolean isIsbnInDb(String isbn) {
        return bookRepository.existsByIsbn(isbn);
    }

    @Transactional
    public void delete(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Livre introuvable pour l'id : " + id);
        }

        bookRepository.deleteById(id);
    }

    @Transactional
    public BookResponseDTO update(Long id, BookCreateDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livre introuvable pour l'id : " + id));

        //Slug et isbn vérifier !!
        bookMapper.updateEntityFromDTO(book, dto);
        return bookMapper.toDTO(bookRepository.save(book));
    }

    /**
     * Génère un slug unique pour un livre.
     * Si le slug existe déjà, un suffixe numérique est ajouté puis incrémenté jusqu'à obtenir une valeur disponible.
     */
    private String createSlug(String slug, Integer number){
        String newSlug;
        if (number == 1){
            newSlug = slug;
        }
        else{
            newSlug = slug+"-"+number;
        }
        if (bookRepository.existsBySlug(newSlug)){
            return createSlug(slug,(number+1));
        }
        return newSlug;
    }
}
