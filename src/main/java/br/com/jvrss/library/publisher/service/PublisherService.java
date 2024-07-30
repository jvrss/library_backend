// PublisherService.java
package br.com.jvrss.library.publisher.service;

import br.com.jvrss.library.publisher.model.Publisher;

import java.util.List;
import java.util.UUID;

public interface PublisherService {
    Publisher createPublisher(Publisher publisher);
    Publisher getPublisherById(UUID id);
    Publisher updatePublisher(UUID id, Publisher publisher);
    void deletePublisher(UUID id);
    List<Publisher> getAllPublishers();
}