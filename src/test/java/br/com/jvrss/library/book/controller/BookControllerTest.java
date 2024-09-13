// src/test/java/br/com/jvrss/library/book/controller/BookControllerTest.java
package br.com.jvrss.library.book.controller;

import br.com.jvrss.library.author.model.Author;
import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.book.service.BookService;
import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.util.JwtUtil;
import br.com.jvrss.library.filter.JwtRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ComponentScan(basePackageClasses = {JwtUtil.class, JwtRequestFilter.class})
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WebApplicationContext context;

    private String jwtToken;

    private Book book;

    @BeforeEach
    void setUp() {
        jwtToken = jwtUtil.generateToken("testUser");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle("Sample Book");
        book.setIsbn("123-4567890123");
        book.setPages(300);
        book.setPublication(LocalDate.EPOCH);
        book.setDescription("Sample Description");

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
    @WithMockUser
    void testCreateBook() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Sample Book"));
    }

    @Test
    @WithMockUser
    void testGetBookById() throws Exception {
        when(bookService.getBookById(book.getId())).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/{id}", book.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Book"));
    }

    @Test
    @WithMockUser
    void testUpdateBook() throws Exception {
        when(bookService.updateBook(any(UUID.class), any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/api/books/{id}", book.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Book"));
    }

    @Test
    @WithMockUser
    void testDeleteBook() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/books/{id}", id)
                        .with(csrf())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.singletonList(book));

        mockMvc.perform(get("/api/books")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Sample Book"));
    }

    @Test
    @WithMockUser
    void testHandleValidationExceptions() throws Exception {
        Book book = new Book();
        book.setTitle(""); // Invalid title

        mockMvc.perform(post("/api/books")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("must not be blank"));
    }
}