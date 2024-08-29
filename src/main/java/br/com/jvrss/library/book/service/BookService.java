package br.com.jvrss.library.book.service;

import br.com.jvrss.library.book.model.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(UUID id);
    List<Book> getAllBooks();
    Book updateBook(UUID id, Book book);
    void deleteBook(UUID id);
}