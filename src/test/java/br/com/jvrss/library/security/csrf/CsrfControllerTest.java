package br.com.jvrss.library.security.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CsrfController.class)
public class CsrfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCsrfToken() throws Exception {
        mockMvc.perform(get("/csrf-token")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> {
                    CsrfToken csrfToken = (CsrfToken) result.getRequest().getAttribute(CsrfToken.class.getName());
                    assert csrfToken != null;
                    assert csrfToken.getToken() != null;
                });
    }
}