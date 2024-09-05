package br.com.jvrss.library.author.controller;

import br.com.jvrss.library.author.model.Author;
import br.com.jvrss.library.author.service.AuthorService;
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

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthorController.class)
@ComponentScan(basePackageClasses = {JwtUtil.class, JwtRequestFilter.class})
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WebApplicationContext context;

    private String jwtToken;

    @BeforeEach
    void setUp() {
        jwtToken = jwtUtil.generateToken("testUser");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser
    void testCreateAuthor() throws Exception {
        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("John Doe");

        when(authorService.createAuthor(any(Author.class))).thenReturn(author);

        mockMvc.perform(post("/api/authors")
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @WithMockUser
    void testGetAuthorById() throws Exception {
        UUID id = UUID.randomUUID();
        Author author = new Author();
        author.setId(id);
        author.setName("John Doe");

        when(authorService.getAuthorById(id)).thenReturn(Optional.of(author));

        mockMvc.perform(get("/api/authors/{id}", id)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @WithMockUser
    void testUpdateAuthor() throws Exception {
        UUID id = UUID.randomUUID();
        Author author = new Author();
        author.setId(id);
        author.setName("John Doe");

        when(authorService.updateAuthor(any(UUID.class), any(Author.class))).thenReturn(author);

        mockMvc.perform(put("/api/authors/{id}", id)
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @WithMockUser
    void testDeleteAuthor() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/authors/{id}", id)
                        .with(csrf())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAllAuthors() throws Exception {
        Author author = new Author();
        author.setId(UUID.randomUUID());
        author.setName("John Doe");

        when(authorService.getAllAuthors()).thenReturn(Collections.singletonList(author));

        mockMvc.perform(get("/api/authors")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    @WithMockUser
    void testHandleValidationExceptions() throws Exception {
        Author author = new Author();
        author.setName(""); // Invalid name

        mockMvc.perform(post("/api/authors")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(author)))
                .andExpect(status().isForbidden());
    }
}