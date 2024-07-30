package br.com.jvrss.library.language.controller;

import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.language.service.LanguageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @PostMapping
    public Language createLanguage(@Valid @RequestBody Language language) {
        return languageService.createLanguage(language);
    }

    @GetMapping("/{id}")
    public Language getLanguageById(@PathVariable UUID id) {
        return languageService.getLanguageById(id);
    }

    @PutMapping("/{id}")
    public Language updateLanguage(@PathVariable UUID id, @Valid @RequestBody Language language) {
        return languageService.updateLanguage(id, language);
    }

    @DeleteMapping("/{id}")
    public void deleteLanguage(@PathVariable UUID id) {
        languageService.deleteLanguage(id);
    }

    @GetMapping
    public List<Language> getAllLanguages() {
        return languageService.getAllLanguages();
    }
}