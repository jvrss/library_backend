// PublisherServiceImpl.java
package br.com.jvrss.library.publisher.service.impl;

import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.publisher.repository.PublisherRepository;
import br.com.jvrss.library.publisher.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PublisherServiceImpl implements PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getPublisherById(UUID id) {
        return publisherRepository.findById(id).orElse(null);
    }

    @Override
    public Publisher updatePublisher(UUID id, Publisher publisher) {
        if (publisherRepository.existsById(id)) {
            publisher.setId(id);
            return publisherRepository.save(publisher);
        }
        return null;
    }

    @Override
    public void deletePublisher(UUID id) {
        publisherRepository.deleteById(id);
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }
}