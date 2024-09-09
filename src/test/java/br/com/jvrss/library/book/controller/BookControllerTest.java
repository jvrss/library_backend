package br.com.jvrss.library.book.controller;

import br.com.jvrss.library.author.model.Author;
import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.book.service.BookService;
import br.com.jvrss.library.filter.JwtRequestFilter;
import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.util.JwtUtil;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
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
    private UUID bookId;

    @BeforeEach
    public void setUp() {
        jwtToken = jwtUtil.generateToken("testUser");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        bookId = UUID.randomUUID();
        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("Sample Author");

        Language language = new Language();
        language.setId(UUID.randomUUID());
        language.setName("English");

        Publisher publisher = new Publisher();
        publisher.setId(UUID.randomUUID());
        publisher.setName("Sample Publisher");

        book = new Book();
        book.setId(bookId);
        book.setName("Sample Book");
        book.setDescription("Sample Description");
        book.setIsbn("1234567890123");
        book.setPages(100);
        book.setPublication(LocalDate.now());
        book.setLanguage(language);
        book.setAuthor(author);
        book.setPublisher(publisher);
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetBookById() throws Exception {
        when(bookService.getBookById(bookId)).thenReturn(Optional.ofNullable(book));

        mockMvc.perform(get("/api/books/{id}", bookId)
                .header("Authorization", "Bearer " + jwtToken)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sample Book"))
                .andExpect(jsonPath("$.author.name").value("Sample Author"));
    }

    @Test
    @WithMockUser
    public void testCreateBook() throws Exception {
        when(bookService.createBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .header("Authorization", "Bearer " + jwtToken)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sample Book"))
                .andExpect(jsonPath("$.author.name").value("Sample Author"));
    }

    @Test
    @WithMockUser
    public void testUpdateBook() throws Exception {
        when(bookService.updateBook(eq(bookId), any(Book.class))).thenReturn(book);

        book.setName("Updated Book");
        book.getAuthor().setName("Updated Author");

        mockMvc.perform(put("/api/books/{id}", bookId)
                .header("Authorization", "Bearer " + jwtToken)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Book"))
                .andExpect(jsonPath("$.author.name").value("Updated Author"));
    }

    @Test
    @WithMockUser
    public void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(bookId);

        mockMvc.perform(delete("/api/books/{id}", bookId)
                .header("Authorization", "Bearer " + jwtToken)
                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    public void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of(book));

        mockMvc.perform(get("/api/books")
                .header("Authorization", "Bearer " + jwtToken)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Sample Book"))
                .andExpect(jsonPath("$[0].author.name").value("Sample Author"));
    }
}