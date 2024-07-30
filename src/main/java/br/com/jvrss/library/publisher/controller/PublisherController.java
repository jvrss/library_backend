// src/main/java/br/com/jvrss/library/publisher/controller/PublisherController.java
package br.com.jvrss.library.publisher.controller;

import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.publisher.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/publishers")
public class PublisherController {

    @Autowired
    private PublisherService publisherService;

    @PostMapping
    public Publisher createPublisher(@Valid @RequestBody Publisher publisher) {
        return publisherService.createPublisher(publisher);
    }

    @GetMapping("/{id}")
    public Publisher getPublisherById(@PathVariable UUID id) {
        return publisherService.getPublisherById(id);
    }

    @PutMapping("/{id}")
    public Publisher updatePublisher(@PathVariable UUID id, @Valid @RequestBody Publisher publisher) {
        return publisherService.updatePublisher(id, publisher);
    }

    @DeleteMapping("/{id}")
    public void deletePublisher(@PathVariable UUID id) {
        publisherService.deletePublisher(id);
    }

    @GetMapping
    public List<Publisher> getAllPublishers() {
        return publisherService.getAllPublishers();
    }
}