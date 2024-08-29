package br.com.jvrss.library.book.service.impl;

import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.book.repository.BookRepository;
import br.com.jvrss.library.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(UUID id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(UUID id, Book book) {
        if (bookRepository.existsById(id)) {
            book.setId(id);
            return bookRepository.save(book);
        }
        return null;
    }

    @Override
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}