package br.com.jvrss.library.language.controller;

import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.language.service.LanguageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LanguageControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LanguageService languageService;

    @InjectMocks
    private LanguageController languageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(languageController).build();
    }

    @Test
    void testCreateLanguage() throws Exception {
        Language language = new Language();
        language.setId(UUID.randomUUID());
        language.setName("English");

        when(languageService.createLanguage(any(Language.class))).thenReturn(language);

        mockMvc.perform(post("/api/languages")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"English\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("English"));
    }

    @Test
    void testGetLanguageById() throws Exception {
        UUID id = UUID.randomUUID();
        Language language = new Language();
        language.setId(id);
        language.setName("English");

        when(languageService.getLanguageById(id)).thenReturn(language);

        mockMvc.perform(get("/api/languages/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("English"));
    }

    @Test
    void testGetAllLanguages() throws Exception {
        Language language = new Language();
        language.setId(UUID.randomUUID());
        language.setName("English");
        List<Language> languages = Collections.singletonList(language);

        when(languageService.getAllLanguages()).thenReturn(languages);

        mockMvc.perform(get("/api/languages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("English"));
    }

    @Test
    void testUpdateLanguage() throws Exception {
        UUID id = UUID.randomUUID();
        Language language = new Language();
        language.setId(id);
        language.setName("English");

        when(languageService.updateLanguage(any(UUID.class), any(Language.class))).thenReturn(language);

        mockMvc.perform(put("/api/languages/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"English\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("English"));
    }

    @Test
    void testDeleteLanguage() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/languages/{id}", id))
                .andExpect(status().isNoContent());
    }
}