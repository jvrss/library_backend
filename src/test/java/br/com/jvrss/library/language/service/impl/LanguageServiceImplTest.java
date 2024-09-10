package br.com.jvrss.library.language.service.impl;

import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.language.repository.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LanguageServiceImplTest {

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageServiceImpl languageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLanguage() {
        Language language = new Language();
        language.setId(UUID.randomUUID());
        language.setName("English");

        when(languageRepository.save(any(Language.class))).thenReturn(language);

        Language createdLanguage = languageService.createLanguage(language);

        assertThat(createdLanguage).isNotNull();
        assertThat(createdLanguage.getName()).isEqualTo("English");
        verify(languageRepository, times(1)).save(language);
    }

    @Test
    void testGetLanguageById() {
        UUID id = UUID.randomUUID();
        Language language = new Language();
        language.setId(id);
        language.setName("English");

        when(languageRepository.findById(id)).thenReturn(Optional.of(language));

        Language foundLanguage = languageService.getLanguageById(id);

        assertThat(foundLanguage).isNotNull();
        assertThat(foundLanguage.getName()).isEqualTo("English");
        verify(languageRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateLanguage() {
        UUID id = UUID.randomUUID();
        Language language = new Language();
        language.setId(id);
        language.setName("English");

        when(languageRepository.existsById(id)).thenReturn(true);
        when(languageRepository.save(any(Language.class))).thenReturn(language);

        Language updatedLanguage = languageService.updateLanguage(id, language);

        assertThat(updatedLanguage).isNotNull();
        assertThat(updatedLanguage.getName()).isEqualTo("English");
        verify(languageRepository, times(1)).existsById(id);
        verify(languageRepository, times(1)).save(language);
    }

    @Test
    void testDeleteLanguage() {
        UUID id = UUID.randomUUID();

        doNothing().when(languageRepository).deleteById(id);

        languageService.deleteLanguage(id);

        verify(languageRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAllLanguages() {
        Language language = new Language();
        language.setId(UUID.randomUUID());
        language.setName("English");
        List<Language> languages = Collections.singletonList(language);

        when(languageRepository.findAll()).thenReturn(languages);

        List<Language> allLanguages = languageService.getAllLanguages();

        assertThat(allLanguages).isNotNull();
        assertThat(allLanguages).hasSize(1);
        assertThat(allLanguages.get(0).getName()).isEqualTo("English");
        verify(languageRepository, times(1)).findAll();
    }
}