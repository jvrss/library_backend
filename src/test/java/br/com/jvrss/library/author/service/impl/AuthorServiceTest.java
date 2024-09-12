package br.com.jvrss.library.author.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.jvrss.library.author.model.Author;
import br.com.jvrss.library.author.repository.AuthorRepository;
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
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService; // Use the implementation class

    private Author author;
    private UUID authorId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authorId = UUID.randomUUID();
        author = new Author();
        author.setId(authorId);
        author.setName("John Doe");
    }

    @Test
    public void testFindAuthorById() {
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        Optional<Author> foundAuthor = authorService.getAuthorById(authorId);

        assertTrue(foundAuthor.isPresent());
        assertEquals("John Doe", foundAuthor.get().getName());
    }

    @Test
    public void testSaveAuthor() {
        when(authorRepository.save(author)).thenReturn(author);

        Author savedAuthor = authorService.createAuthor(author);

        assertNotNull(savedAuthor);
        assertEquals("John Doe", savedAuthor.getName());
    }

    @Test
    public void testUpdateAuthor() {
        when(authorRepository.existsById(authorId)).thenReturn(true);
        when(authorRepository.save(author)).thenReturn(author);

        Optional<Author> updatedAuthor = authorService.updateAuthor(authorId, author);

        if (updatedAuthor.isEmpty()) {
            fail("Author not found");
        }

        assertNotNull(updatedAuthor.get());
        assertEquals("John Doe", updatedAuthor.get().getName());
    }

    @Test
    public void testDeleteAuthor() {
        doNothing().when(authorRepository).deleteById(authorId);

        assertDoesNotThrow(() -> authorService.deleteAuthor(authorId));
        verify(authorRepository, times(1)).deleteById(authorId);
    }

    @Test
    public void testGetAllAuthors() {
        when(authorRepository.findAll()).thenReturn(List.of(author));

        List<Author> authors = authorService.getAllAuthors();

        assertNotNull(authors);
        assertEquals(1, authors.size());
        assertEquals("John Doe", authors.get(0).getName());
    }
}