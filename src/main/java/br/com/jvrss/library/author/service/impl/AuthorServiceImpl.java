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

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public Optional<Author> getAuthorById(UUID id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author updateAuthor(UUID id, Author author) {
        if (authorRepository.existsById(id)) {
            author.setId(id);
            return authorRepository.save(author);
        } else {
            throw new RuntimeException("Author not found");
        }
    }

    @Override
    public void deleteAuthor(UUID id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}