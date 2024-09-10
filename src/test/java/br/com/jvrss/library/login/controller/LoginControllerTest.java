package br.com.jvrss.library.login.controller;

import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.service.LoginService;
import br.com.jvrss.library.util.JwtUtil;
import br.com.jvrss.library.filter.JwtRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@ComponentScan(basePackageClasses = {JwtUtil.class, JwtRequestFilter.class})
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WebApplicationContext context;

    private String jwtToken;

    private Login login;

    @BeforeEach
    void setUp() {
        jwtToken = jwtUtil.generateToken("testUser");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        login = new Login();
        login.setId(UUID.randomUUID());
        login.setName("johndoe");
        login.setEmail("johndoe@example.com");
        login.setPassword("password");
    }

    @Test
    @WithMockUser
    void testCreateLogin() throws Exception {
        when(loginService.createLogin(any(Login.class))).thenReturn(login);

        mockMvc.perform(post("/api/logins")
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("johndoe"));
    }

    @Test
    @WithMockUser
    void testGetLoginById() throws Exception {
        when(loginService.getLoginById(login.getId())).thenReturn(Optional.of(login));

        mockMvc.perform(get("/api/logins/{id}", login.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("johndoe"));
    }

    @Test
    @WithMockUser
    void testUpdateLogin() throws Exception {
        when(loginService.updateLogin(any(UUID.class), any(Login.class))).thenReturn(login);

        mockMvc.perform(put("/api/logins/{id}", login.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("johndoe"));
    }

    @Test
    @WithMockUser
    void testDeleteLogin() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/logins/{id}", id)
                        .with(csrf())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAllLogins() throws Exception {
        when(loginService.getAllLogins()).thenReturn(Collections.singletonList(login));

        mockMvc.perform(get("/api/logins")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("johndoe"));
    }
}