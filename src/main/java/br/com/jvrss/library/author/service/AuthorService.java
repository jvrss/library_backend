// src/main/java/br/com/jvrss/library/author/service/AuthorService.java
package br.com.jvrss.library.author.service;

import br.com.jvrss.library.author.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorService {
    Author createAuthor(Author author);
    Optional<Author> getAuthorById(UUID id);
    Author updateAuthor(UUID id, Author author);
    void deleteAuthor(UUID id);
    List<Author> getAllAuthors();
}