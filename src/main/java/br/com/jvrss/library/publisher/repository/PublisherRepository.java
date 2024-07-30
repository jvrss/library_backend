package br.com.jvrss.library.publisher.repository;

import br.com.jvrss.library.publisher.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
}