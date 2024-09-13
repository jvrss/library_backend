package br.com.jvrss.library.book.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.jvrss.library.author.model.Author;
import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.book.repository.BookRepository;
import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.publisher.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private UUID bookId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookId = UUID.randomUUID();
        book = new Book();
        book.setId(bookId);
        book.setTitle("Sample Book");
        book.setDescription("A sample book");
        book.setPublication(LocalDate.EPOCH);
        book.setIsbn("1234567890");
        book.setPages(100);

        Publisher publisher = new Publisher();
        publisher.setId(UUID.randomUUID());
        publisher.setName("Sample Publisher");
        publisher.setAddress("Sample Address");
        book.setPublisher(publisher);

        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("John Doe");
        author.setBio("Sample Bio");
        author.setBirthday(LocalDate.EPOCH);
        book.setAuthor(author);

        Language language = new Language();
        language.setId(UUID.randomUUID());
        language.setName("English");
        book.setLanguage(language);

    }

    @Test
    public void testFindBookById() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(bookId);

        assertTrue(foundBook.isPresent());
        assertEquals("Sample Book", foundBook.get().getTitle());
    }

    @Test
    public void testSaveBook() {
        // Mock the save method to return the book object
        when(bookRepository.save(book)).thenReturn(book);

        // Call the createBook method
        Book savedBook = bookService.createBook(book);

        // Assert that the saved book is not null and has the expected title
        assertNotNull(savedBook, "The saved book should not be null");
        assertEquals("Sample Book", savedBook.getTitle());
    }

    @Test
    public void testUpdateBook() {
        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookRepository.save(book)).thenReturn(book);

        book.setTitle("Updated Book");

        Book updatedBook = bookService.updateBook(bookId, book);

        assertNotNull(updatedBook);
        assertEquals("Updated Book", updatedBook.getTitle());
    }

    @Test
    public void testDeleteBook() {
        doNothing().when(bookRepository).deleteById(bookId);

        assertDoesNotThrow(() -> bookService.deleteBook(bookId));
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<Book> books = bookService.getAllBooks();

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals("Sample Book", books.get(0).getTitle());
    }
}