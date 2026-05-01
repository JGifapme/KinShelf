package com.kinshelf.services;

import com.kinshelf.dto.book.BookFromApiDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DataFromOtherApiService {
    
    //RestTemplate sert à transformer la réponse JSON de l'API en une Map
    private final RestTemplate restTemplate = new RestTemplate();
    
    public BookFromApiDTO bookByIsbn(String isbn) {
        // On teste OpenLibrary en premier car les livres en Fr semble mieux décrits
        BookFromApiDTO openLibraryDto = recupDeOpenLibrary(isbn);

        // On récupère aussi les données de Google Books
        BookFromApiDTO googleDto = recupDeGoogleBooks(isbn);

        // Si les deux sont null on retourne null
        if (openLibraryDto == null && googleDto == null) {
            return null;
        }
        // Si OpenLibrary est null, on retourne google
        else if (openLibraryDto == null) {
            return googleDto;
        }
        // Sinon l'inverse
        else if (googleDto == null) {
            return openLibraryDto;
        }
        // Si on a les deux, on merge les données des deux
        else {
            String title;
            if (openLibraryDto.title() != null && !openLibraryDto.title().isEmpty()) {
                title = openLibraryDto.title();
            }else {title = googleDto.title();}

            String publisher;
            if (openLibraryDto.publisher() != null && !openLibraryDto.publisher().isEmpty()) {
                publisher = openLibraryDto.publisher();
            }else {publisher = googleDto.publisher();}

            List<String> authors;
            if (openLibraryDto.authors() != null || openLibraryDto.authors().size() > 0) {
                authors = openLibraryDto.authors();
            }else {authors = googleDto.authors();}

            String description;
            if (openLibraryDto.description() != null && !openLibraryDto.description().isEmpty()) {
                description = openLibraryDto.description();
            }else {description = googleDto.description();}

            Integer pageCount;
            if (openLibraryDto.pageCount() != null && openLibraryDto.pageCount() >= 0) {
                pageCount = openLibraryDto.pageCount();
            }else {pageCount = googleDto.pageCount();}

            String imageUrl;
            if (openLibraryDto.imageUrl() != null && !openLibraryDto.imageUrl().isEmpty()) {
                imageUrl = openLibraryDto.imageUrl();
            }else {imageUrl = googleDto.imageUrl();}

            String date;
            if (openLibraryDto.publicationDate() != null && !openLibraryDto.publicationDate().isEmpty()) {
                date = openLibraryDto.publicationDate();
            }else {date = googleDto.publicationDate();}

            BookFromApiDTO dto = new BookFromApiDTO(
                        title,
                        publisher,
                        authors,
                        description,
                        isbn,
                        pageCount,
                        imageUrl,
                        date
                );
            return dto;
        }
    }

    private BookFromApiDTO recupDeOpenLibrary(String isbn) {
        // url de l'api Open Library + isbn de l'ouvrage
        String url = "https://openlibrary.org/api/books?bibkeys=ISBN:" + isbn + "&format=json&jscmd=data";

        try {
            //on récupère les données du json de l'api Open Library dans un Map
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            String key = "ISBN:" + isbn;

            //si le livre n'est pas trouvé on renvoie null
            if (response == null || !response.containsKey(key)){
                return null;
            }

            Map<String, Object> bookData = (Map<String, Object>) response.get(key);

            // extraction des auteurs
            List<String> authorNames = new ArrayList<>();
            if (bookData.containsKey("authors")) {
                List<Map<String, String>> authors = (List<Map<String, String>>) bookData.get("authors");
                authors.forEach(a -> authorNames.add(a.get("name")));
            }

            // Extraction de l'image
            String imageUrl = null;
            if (bookData.containsKey("cover")) {
                Map<String, String> covers = (Map<String, String>) bookData.get("cover");
                imageUrl = covers.get("medium");
            }

            //la date
            String rawDate = (String) bookData.get("publish_date"); // Pour OpenLibrary
// ou (String) volumeInfo.get("publishedDate"); // Pour Google
            String formattedDate = normalizeDate(rawDate);

            return new BookFromApiDTO(
                    (String) bookData.get("title"),
                    bookData.containsKey("publishers") ? ((List<Map<String, String>>) bookData.get("publishers")).get(0).get("name") : null,
                    authorNames,
                    (String) bookData.get("description"),
                    isbn,
                    (Integer) bookData.get("number_of_pages"),
                    imageUrl,
                    formattedDate
            );
        } catch (Exception e) {
            //si il y a un problème on ne renvoie rien et l'utilisateur devra ajouter toutes les infos manuellement
            return null;
        }
    }

    private BookFromApiDTO recupDeGoogleBooks(String isbn) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn;
        try {
            //on récupère les données du json de l'api Open Library dans un Map
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> items = (List<Map<String, Object>>) response.get("items");

            //si le livre n'est pas trouvé ou qu'il y a une erreur dans la récupération des données on renvoie null
            if (items == null || items.isEmpty()){
                return null;
            }

            Map<String, Object> volumeInfo = (Map<String, Object>) items.get(0).get("volumeInfo");

            // Extraction de l'image
            String imageUrl = null;
            if (volumeInfo.containsKey("imageLinks")) {
                Map<String, String> imageLinks = (Map<String, String>) volumeInfo.get("imageLinks");
                imageUrl = imageLinks.get("thumbnail");
            }

            //la date
            String rawDate = (String) volumeInfo.get("publishedDate");
            String formattedDate = normalizeDate(rawDate);

            //ensuite on renvoie un dto qui servira à pré-remplir les champs lorsqu'un utilisateur ajoute un livre
            return new BookFromApiDTO(
                    (String) volumeInfo.get("title"),
                    (String) volumeInfo.get("publisher"),
                    (List<String>) volumeInfo.get("authors"),
                    (String) volumeInfo.get("description"),
                    isbn,
                    (Integer) volumeInfo.get("pageCount"),
                    imageUrl,
                    formattedDate
            );
        } catch (Exception e) {
            //si il y a un problème on ne renvoie rien et l'utilisateur devra ajouter toutes les infos manuellement
            return null;
        }
    }

    //Méthode pour transformer la date dans le bon format
    private String normalizeDate(String rawDate) {
        if (rawDate == null || rawDate.isEmpty()) return null;

        // Si le format de la date est bon on le garde
        if (rawDate.matches("\\d{4}-\\d{2}-\\d{2}")) return rawDate;

        // Si on n'a que l'année, on met au 1er janvier
        if (rawDate.matches("\\d{4}")) return rawDate + "-01-01";

        // Si c'est en texte, on récupère l'année et on met au premier janvier, à adapter pour faire mieux
        Pattern pattern = Pattern.compile("(\\d{4})");
        Matcher matcher = pattern.matcher(rawDate);
        if (matcher.find()) {
            return matcher.group(1) + "-01-01";
        }

        return null;
    }
}
