package br.com.jvrss.library.user.controller;

import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.user.model.User;
import br.com.jvrss.library.user.service.UserService;
import br.com.jvrss.library.util.CPFGenerator;
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

@WebMvcTest(UserController.class)
@ComponentScan(basePackageClasses = {JwtUtil.class, JwtRequestFilter.class})
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WebApplicationContext context;

    private String jwtToken;

    private User user;

    @BeforeEach
    void setUp() {
        jwtToken = jwtUtil.generateToken("testUser");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        user = new User();
        user.setCpf(CPFGenerator.generateCPF());

        Login login = new Login();
        login.setId(UUID.randomUUID());
        login.setName("johndoe");
        login.setEmail("johndoe@example.com");
        login.setPassword("password");

        user.setLogin(login);
    }

    @Test
    @WithMockUser
    void testCreateUser() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpf").value(user.getCpf()));
    }

    @Test
    @WithMockUser
    void testGetUserById() throws Exception {
        when(userService.getUserByCpf(user.getCpf())).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{id}", user.getCpf())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(user.getCpf()));
    }

    @Test
    @WithMockUser
    void testUpdateUser() throws Exception {
        when(userService.updateUser(any(String.class), any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/{id}", user.getCpf())
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cpf").value(user.getCpf()));
    }

    @Test
    @WithMockUser
    void testDeleteUser() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/users/{id}", id)
                        .with(csrf())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/users")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cpf").value(user.getCpf()));
    }
}