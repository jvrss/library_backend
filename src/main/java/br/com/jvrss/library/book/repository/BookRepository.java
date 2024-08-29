package br.com.jvrss.library.book.repository;

import br.com.jvrss.library.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
}