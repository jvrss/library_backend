package br.com.jvrss.library.book.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.book.repository.BookRepository;
import br.com.jvrss.library.book.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

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
        book.setName("Sample Book");
        // Set other properties of the book as needed
    }

    @Test
    public void testFindBookById() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> foundBook = bookService.getBookById(bookId);

        assertTrue(foundBook.isPresent());
        assertEquals("Sample Book", foundBook.get().getName());
    }

    @Test
    public void testSaveBook() {
        when(bookRepository.save(book)).thenReturn(book);

        Book savedBook = bookService.createBook(book);

        assertNotNull(savedBook, "The saved book should not be null");
        assertEquals("Sample Book", savedBook.getName());
    }

    @Test
    public void testUpdateBook() {
        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(bookRepository.save(book)).thenReturn(book);

        book.setName("Updated Book");

        Book updatedBook = bookService.updateBook(bookId, book);

        assertNotNull(updatedBook);
        assertEquals("Updated Book", updatedBook.getName());
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
        assertEquals("Sample Book", books.get(0).getName());
    }
}