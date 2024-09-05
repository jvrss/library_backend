package br.com.jvrss.library.author.repository;

import br.com.jvrss.library.author.model.Author;
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

    @Test
    void testSaveAuthor() {
        UUID expectedId = UUID.randomUUID();
        Author author = new Author();
        author.setName("John Doe");

        Author savedAuthor = authorRepository.save(author);

        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindById() {
        Author author = new Author();
        author.setName("John Doe");

        Author savedAuthor = authorRepository.save(author);

        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());

        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("John Doe");
    }

    @Test
    void testFindAll() {
        Author author1 = new Author();
        author1.setId(UUID.randomUUID());
        author1.setName("John Doe");

        Author author2 = new Author();
        author2.setId(UUID.randomUUID());
        author2.setName("Jane Doe");

        authorRepository.save(author1);
        authorRepository.save(author2);

        List<Author> authors = authorRepository.findAll();

        assertThat(authors).hasSize(2);
        assertThat(authors).extracting(Author::getName).containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        Author author = new Author();
        author.setId(id);
        author.setName("John Doe");

        authorRepository.save(author);
        authorRepository.deleteById(id);

        Optional<Author> deletedAuthor = authorRepository.findById(id);

        assertThat(deletedAuthor).isNotPresent();
    }
}