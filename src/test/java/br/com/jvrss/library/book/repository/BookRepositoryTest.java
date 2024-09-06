package br.com.jvrss.library.book.repository;

import br.com.jvrss.library.author.model.Author;
import br.com.jvrss.library.author.repository.AuthorRepository;
import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.publisher.repository.PublisherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    private Book book;
    private Author author;
    private Publisher publisher;

    @BeforeEach
    public void setUp() {
        author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("Sample Author");
        authorRepository.save(author); // Save the author

        publisher = new Publisher();
        publisher.setId(UUID.randomUUID());
        publisher.setName("Sample Publisher");
        publisherRepository.save(publisher); // Save the publisher

        book = new Book();
        book.setName("Sample Book");
        book.setAuthor(author);
        book.setPublisher(publisher);

        book = bookRepository.save(book);
    }

    @Test
    public void testSaveBook() {
        Book savedBook = bookRepository.save(book);
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isEqualTo(book.getId());
    }

    @Test
    public void testFindById() {
        bookRepository.save(book);
        Optional<Book> foundBook = bookRepository.findById(book.getId());
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getName()).isEqualTo("Sample Book");
    }

    @Test
    public void testDeleteBook() {
        bookRepository.save(book);
        bookRepository.deleteById(book.getId());
        Optional<Book> deletedBook = bookRepository.findById(book.getId());
        assertThat(deletedBook).isNotPresent();
    }
}