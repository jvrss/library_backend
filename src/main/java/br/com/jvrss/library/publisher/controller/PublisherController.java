package br.com.jvrss.library.publisher.controller;

import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.publisher.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * REST controller for handling publisher-related requests.
 */
@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    /**
     * Creates a new publisher.
     *
     * @param publisher the publisher to create
     * @return the created publisher
     */
    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@Valid @RequestBody Publisher publisher) {
        Publisher createdPublisher = publisherService.createPublisher(publisher);
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    /**
     * Retrieves a publisher by its ID.
     *
     * @param id the ID of the publisher
     * @return the publisher if found, or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable UUID id) {
        Optional<Publisher> publisher = publisherService.getPublisherById(id);
        return publisher.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Updates an existing publisher.
     *
     * @param id the ID of the publisher to update
     * @param publisher the updated publisher data
     * @return the updated publisher if found, or a 404 Not Found response if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable UUID id, @Valid @RequestBody Publisher publisher) {
        Publisher updatedPublisher = publisherService.updatePublisher(id, publisher);
        if (updatedPublisher != null) {
            return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a publisher by its ID.
     *
     * @param id the ID of the publisher to delete
     * @return a 204 No Content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable UUID id) {
        publisherService.deletePublisher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves all publishers.
     *
     * @return a list of all publishers
     */
    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }
}