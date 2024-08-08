// src/main/java/br/com/jvrss/library/author/repository/AuthorRepository.java
package br.com.jvrss.library.author.repository;

import br.com.jvrss.library.author.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthorRepository extends JpaRepository<Author, UUID> {
}