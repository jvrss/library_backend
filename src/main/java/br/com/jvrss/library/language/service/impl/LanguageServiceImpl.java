// LanguageServiceImpl.java
package br.com.jvrss.library.language.service;

import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.language.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LanguageServiceImpl implements br.com.jvrss.library.language.service.LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    @Override
    public Language createLanguage(Language language) {
        return languageRepository.save(language);
    }

    @Override
    public Language getLanguageById(UUID id) {
        return languageRepository.findById(id).orElse(null);
    }

    @Override
    public Language updateLanguage(UUID id, Language language) {
        if (languageRepository.existsById(id)) {
            language.setId(id);
            return languageRepository.save(language);
        }
        return null;
    }

    @Override
    public void deleteLanguage(UUID id) {
        languageRepository.deleteById(id);
    }

    @Override
    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }
}