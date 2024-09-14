package br.com.jvrss.library.publisher.service;

import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.publisher.repository.PublisherRepository;
import br.com.jvrss.library.publisher.service.impl.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceImplTest {

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherServiceImpl publisherService;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new Publisher();
        publisher.setId(UUID.randomUUID());
        publisher.setName("Test Publisher");
    }

    @Test
    void testCreatePublisher() {
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        Publisher createdPublisher = publisherService.createPublisher(publisher);

        assertThat(createdPublisher).isNotNull();
        assertThat(createdPublisher.getName()).isEqualTo("Test Publisher");
        verify(publisherRepository, times(1)).save(publisher);
    }

    @Test
    void testGetPublisherById() {
        when(publisherRepository.findById(publisher.getId())).thenReturn(Optional.of(publisher));

        Optional<Publisher> foundPublisher = publisherService.getPublisherById(publisher.getId());

        assertThat(foundPublisher).isPresent();
        assertThat(foundPublisher.get().getName()).isEqualTo("Test Publisher");
        verify(publisherRepository, times(1)).findById(publisher.getId());
    }

    @Test
    void testUpdatePublisher() {
        when(publisherRepository.existsById(any(UUID.class))).thenReturn(true);
        when(publisherRepository.save(any(Publisher.class))).thenReturn(publisher);

        Publisher updatedPublisher = publisherService.updatePublisher(publisher.getId(), publisher);

        assertThat(updatedPublisher).isNotNull();
        assertThat(updatedPublisher.getName()).isEqualTo("Test Publisher");
        verify(publisherRepository, times(1)).save(publisher);
    }

    @Test
    void testDeletePublisher() {
        doNothing().when(publisherRepository).deleteById(publisher.getId());

        publisherService.deletePublisher(publisher.getId());

        verify(publisherRepository, times(1)).deleteById(publisher.getId());
    }

    @Test
    void testGetAllPublishers() {
        when(publisherRepository.findAll()).thenReturn(Collections.singletonList(publisher));

        Iterable<Publisher> publishers = publisherService.getAllPublishers();

        assertThat(publishers).hasSize(1);
        verify(publisherRepository, times(1)).findAll();
    }
}