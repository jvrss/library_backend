package br.com.jvrss.library.language.controller;

import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.language.service.LanguageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    private final LanguageService languageService;

    @Autowired
    public LanguageController(LanguageService languageService) {
        this.languageService = languageService;
    }

    @PostMapping
    public ResponseEntity<Language> createLanguage(@Valid @RequestBody Language language) {
        Language createdLanguage = languageService.createLanguage(language);
        return ResponseEntity.ok(createdLanguage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable UUID id) {
        Language language = languageService.getLanguageById(id);
        if (language != null) {
            return ResponseEntity.ok(language);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Language>> getAllLanguages() {
        List<Language> languages = languageService.getAllLanguages();
        return ResponseEntity.ok(languages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Language> updateLanguage(@PathVariable UUID id, @Valid @RequestBody Language language) {
        Language updatedLanguage = languageService.updateLanguage(id, language);
        if (updatedLanguage != null) {
            return ResponseEntity.ok(updatedLanguage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable UUID id) {
        languageService.deleteLanguage(id);
        return ResponseEntity.noContent().build();
    }
}