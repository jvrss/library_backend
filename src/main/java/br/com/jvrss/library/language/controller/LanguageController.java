package br.com.jvrss.library.language.controller;

import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.language.service.LanguageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for handling language-related requests.
 */
@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    /**
     * Constructs a new LanguageController with the specified LanguageService.
     *
     * @param languageService the language service
     */
    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    /**
     * Creates a new language.
     *
     * @param language the language to create
     * @return the created language
     */
    @PostMapping
    public ResponseEntity<Language> createLanguage(@Valid @RequestBody Language language) {
        Language createdLanguage = languageService.createLanguage(language);
        return ResponseEntity.ok(createdLanguage);
    }

    /**
     * Retrieves a language by its ID.
     *
     * @param id the ID of the language
     * @return the language if found, or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable UUID id) {
        Language language = languageService.getLanguageById(id);
        if (language != null) {
            return ResponseEntity.ok(language);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all languages.
     *
     * @return a list of all languages
     */
    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        List<Language> languages = languageService.getAllLanguages();
        return ResponseEntity.ok(languages);
    }

    /**
     * Updates an existing language.
     *
     * @param id the ID of the language to update
     * @param language the updated language data
     * @return the updated language if found, or a 404 Not Found response if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Language> updateLanguage(@PathVariable UUID id, @Valid @RequestBody Language language) {
        Language updatedLanguage = languageService.updateLanguage(id, language);
        if (updatedLanguage != null) {
            return ResponseEntity.ok(updatedLanguage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a language by its ID.
     *
     * @param id the ID of the language to delete
     * @return a 204 No Content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable UUID id) {
        languageService.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }
}