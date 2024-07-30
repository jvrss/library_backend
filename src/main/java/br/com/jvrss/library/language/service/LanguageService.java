// LanguageService.java
package br.com.jvrss.library.language.service;

import br.com.jvrss.library.language.model.Language;
import java.util.List;
import java.util.UUID;

public interface LanguageService {
    Language createLanguage(Language language);
    Language getLanguageById(UUID id);
    Language updateLanguage(UUID id, Language language);
    void deleteLanguage(UUID id);
    List<Language> getAllLanguages();
}