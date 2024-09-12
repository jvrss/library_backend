// src/main/java/br/com/jvrss/library/author/service/impl/AuthorServiceImpl.java
package br.com.jvrss.library.author.service.impl;

import br.com.jvrss.library.author.model.Author;
import br.com.jvrss.library.author.repository.AuthorRepository;
import br.com.jvrss.library.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the AuthorService interface.
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    /**
     * Creates a new author.
     *
     * @param author the author to create
     * @return the created author
     */
    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Retrieves an author by its ID.
     *
     * @param id the ID of the author
     * @return an Optional containing the author if found, or an empty Optional if not found
     */
    @Override
    public Optional<Author> getAuthorById(UUID id) {
        return authorRepository.findById(id);
    }

    /**
     * Updates an existing author.
     *
     * @param id     the ID of the author to update
     * @param author the updated author data
     * @return the updated author
     * @throws RuntimeException if the author is not found
     */
    @Override
    public Optional<Author> updateAuthor(UUID id, Author author) {
        if (authorRepository.existsById(id)) {
            author.setId(id);
            return Optional.of(authorRepository.save(author));
        } else {
            throw new RuntimeException("Author not found");
        }
    }

    /**
     * Deletes an author by its ID.
     *
     * @param id the ID of the author to delete
     */
    @Override
    public void deleteAuthor(UUID id) {
        authorRepository.deleteById(id);
    }

    /**
     * Retrieves all authors.
     *
     * @return a list of all authors
     */
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}