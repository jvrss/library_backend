package br.com.jvrss.library.publisher.repository;

import br.com.jvrss.library.publisher.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PublisherRepositoryTest {

    @Autowired
    private PublisherRepository publisherRepository;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new Publisher();
        publisher.setId(UUID.randomUUID());
        publisher.setName("Test Publisher");
        publisherRepository.save(publisher);
    }

    @Test
    void testSavePublisher() {
        Publisher savedPublisher = publisherRepository.save(publisher);
        assertThat(savedPublisher).isNotNull();
        assertThat(savedPublisher.getId()).isEqualTo(publisher.getId());
    }

    @Test
    void testFindById() {
        Optional<Publisher> foundPublisher = publisherRepository.findById(publisher.getId());
        assertThat(foundPublisher).isPresent();
        assertThat(foundPublisher.get().getName()).isEqualTo("Test Publisher");
    }

    @Test
    void testFindAll() {
        Iterable<Publisher> publishers = publisherRepository.findAll();
        assertThat(publishers).hasSize(1);
    }

    @Test
    void testDeleteById() {
        publisherRepository.deleteById(publisher.getId());
        Optional<Publisher> deletedPublisher = publisherRepository.findById(publisher.getId());
        assertThat(deletedPublisher).isNotPresent();
    }
}