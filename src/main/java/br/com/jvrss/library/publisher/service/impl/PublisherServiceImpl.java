package br.com.jvrss.library.publisher.service.impl;

import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.publisher.repository.PublisherRepository;
import br.com.jvrss.library.publisher.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the PublisherService interface for handling publisher-related operations.
 */
@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    /**
     * Creates a new publisher.
     *
     * @param publisher the publisher to create
     * @return the created publisher
     */
    @Override
    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    /**
     * Retrieves a publisher by its ID.
     *
     * @param id the ID of the publisher
     * @return the publisher if found, or null if not found
     */
    @Override
    public Optional<Publisher> getPublisherById(UUID id) {
        return Optional.ofNullable(publisherRepository.findById(id).orElse(null));
    }

    /**
     * Updates an existing publisher.
     *
     * @param id the ID of the publisher to update
     * @param publisher the updated publisher data
     * @return the updated publisher if found, or null if not found
     */
    @Override
    public Publisher updatePublisher(UUID id, Publisher publisher) {
        if (publisherRepository.existsById(id)) {
            publisher.setId(id);
            return publisherRepository.save(publisher);
        }
        return null;
    }

    /**
     * Deletes a publisher by its ID.
     *
     * @param id the ID of the publisher to delete
     */
    @Override
    public void deletePublisher(UUID id) {
        publisherRepository.deleteById(id);
    }

    /**
     * Retrieves all publishers.
     *
     * @return a list of all publishers
     */
    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }
}