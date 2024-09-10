package br.com.jvrss.library.language.repository;

import br.com.jvrss.library.language.model.Language;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LanguageRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    void testSaveLanguage() {
        Language language = new Language();
        language.setId(UUID.randomUUID());
        language.setName("English");

        Language savedLanguage = languageRepository.save(language);

        assertThat(savedLanguage).isNotNull();
        assertThat(savedLanguage.getId()).isEqualTo(language.getId());
        assertThat(savedLanguage.getName()).isEqualTo("English");
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Language language = new Language();
        language.setId(id);
        language.setName("English");

        languageRepository.save(language);

        Optional<Language> foundLanguage = languageRepository.findById(id);

        assertThat(foundLanguage).isPresent();
        assertThat(foundLanguage.get().getName()).isEqualTo("English");
    }

    @Test
    void testFindAll() {
        Language language1 = new Language();
        language1.setId(UUID.randomUUID());
        language1.setName("English");

        Language language2 = new Language();
        language2.setId(UUID.randomUUID());
        language2.setName("Spanish");

        languageRepository.save(language1);
        languageRepository.save(language2);

        Iterable<Language> languages = languageRepository.findAll();

        assertThat(languages).hasSize(2);
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        Language language = new Language();
        language.setId(id);
        language.setName("English");

        languageRepository.save(language);
        languageRepository.deleteById(id);

        Optional<Language> deletedLanguage = languageRepository.findById(id);

        assertThat(deletedLanguage).isNotPresent();
    }
}