package br.com.jvrss.library.language.repository;

import br.com.jvrss.library.language.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface LanguageRepository extends JpaRepository<Language, UUID> {
}