// src/main/java/br/com/jvrss/library/publisher/controller/PublisherController.java
package br.com.jvrss.library.publisher.controller;

import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.publisher.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping
    public ResponseEntity<Publisher> createPublisher(@Valid @RequestBody Publisher publisher) {
        Publisher createdPublisher = publisherService.createPublisher(publisher);
        return new ResponseEntity<>(createdPublisher, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherById(@PathVariable UUID id) {
        Publisher publisher = publisherService.getPublisherById(id);
        if (publisher != null) {
            return new ResponseEntity<>(publisher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable UUID id, @Valid @RequestBody Publisher publisher) {
        Publisher updatedPublisher = publisherService.updatePublisher(id, publisher);
        if (updatedPublisher != null) {
            return new ResponseEntity<>(updatedPublisher, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable UUID id) {
        publisherService.deletePublisher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        List<Publisher> publishers = publisherService.getAllPublishers();
        return new ResponseEntity<>(publishers, HttpStatus.OK);
    }
}