package br.com.jvrss.library.language.service.impl;

import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.language.repository.LanguageRepository;
import br.com.jvrss.library.language.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Implementation of the LanguageService interface.
 */
@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    /**
     * Creates a new language.
     *
     * @param language the language to create
     * @return the created language
     */
    @Override
    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    /**
     * Retrieves a language by its ID.
     *
     * @param id the ID of the language
     * @return the language if found, or null if not found
     */
    @Override
    public Language getLanguageById(UUID id) {
        return languageRepository.findById(id).orElse(null);
    }

    /**
     * Updates an existing language.
     *
     * @param id the ID of the language to update
     * @param language the updated language data
     * @return the updated language if found, or null if not found
     */
    @Override
    public Language updateLanguage(UUID id, Language language) {
        if (languageRepository.existsById(id)) {
            language.setId(id);
            return languageRepository.save(language);
        }
        return null;
    }

    /**
     * Deletes a language by its ID.
     *
     * @param id the ID of the language to delete
     */
    @Override
    public void deleteLanguage(UUID id) {
        languageRepository.deleteById(id);
    }

    /**
     * Retrieves all languages.
     *
     * @return a list of all languages
     */
    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
}