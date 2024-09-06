package br.com.jvrss.library.book.service.impl;

import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.book.repository.BookRepository;
import br.com.jvrss.library.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the BookService interface.
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Creates a new book.
     *
     * @param book the book to create
     * @return the created book
     */
    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Retrieves a book by its ID.
     *
     * @param id the ID of the book
     * @return the book, or null if not found
     */
    @Override
    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    /**
     * Retrieves all books.
     *
     * @return a list of all books
     */
    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Updates an existing book.
     *
     * @param id the ID of the book to update
     * @param book the updated book data
     * @return the updated book, or null if not found
     */
    @Override
    public Book updateBook(UUID id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return bookRepository.save(book);
        }
        return null;
    }

    /**
     * Deletes a book by its ID.
     *
     * @param id the ID of the book to delete
     */
    @Override
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}