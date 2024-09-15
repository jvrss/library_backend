package br.com.jvrss.library.hello.controller;

import br.com.jvrss.library.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    private String jwtToken;

    @BeforeEach
    void setUp() {
        Mockito.when(jwtUtil.validateToken(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        jwtToken = jwtUtil.generateToken("testUser");
    }

    @Test
    void testSayHello() throws Exception {
        mockMvc.perform(get("/hello")
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
    }
}