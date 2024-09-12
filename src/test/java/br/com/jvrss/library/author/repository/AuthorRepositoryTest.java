package br.com.jvrss.library.author.repository;

import br.com.jvrss.library.author.model.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        author1 = new Author();
        author1.setId(UUID.randomUUID());
        author1.setName("John Doe");

        author2 = new Author();
        author2.setId(UUID.randomUUID());
        author2.setName("Jane Doe");
    }

    @Test
    void testSaveAuthor() {
        Author savedAuthor = authorRepository.save(author1);

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindById() {
        Author savedAuthor = authorRepository.save(author1);

        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());

        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindAll() {
        authorRepository.save(author1);
        authorRepository.save(author2);

        List<Author> authors = authorRepository.findAll();

        assertThat(authors).hasSize(2);
        assertThat(authors).extracting(Author::getName).containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }

    @Test
    void testDeleteById() {
        UUID id = author1.getId();
        authorRepository.save(author1);
        authorRepository.deleteById(id);

        Optional<Author> deletedAuthor = authorRepository.findById(id);

        assertThat(deletedAuthor).isNotPresent();
    }
}