package br.com.jvrss.library.publisher.controller;

import br.com.jvrss.library.publisher.model.Publisher;
import br.com.jvrss.library.publisher.service.PublisherService;
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

@WebMvcTest(PublisherController.class)
@ComponentScan(basePackageClasses = {JwtUtil.class, JwtRequestFilter.class})
public class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private WebApplicationContext context;

    private String jwtToken;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        jwtToken = jwtUtil.generateToken("testUser");
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();

        publisher = new Publisher();
        publisher.setId(UUID.randomUUID());
        publisher.setName("Test Publisher");
    }

    @Test
    @WithMockUser
    void testCreatePublisher() throws Exception {
        when(publisherService.createPublisher(any(Publisher.class))).thenReturn(publisher);

        mockMvc.perform(post("/api/publishers")
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publisher)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Publisher"));
    }

    @Test
    @WithMockUser
    void testGetPublisherById() throws Exception {
        when(publisherService.getPublisherById(publisher.getId())).thenReturn(Optional.of(publisher));

        mockMvc.perform(get("/api/publishers/{id}", publisher.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Publisher"));
    }

    @Test
    @WithMockUser
    void testUpdatePublisher() throws Exception {
        when(publisherService.updatePublisher(any(UUID.class), any(Publisher.class))).thenReturn(publisher);

        mockMvc.perform(put("/api/publishers/{id}", publisher.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(publisher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Publisher"));
    }

    @Test
    @WithMockUser
    void testDeletePublisher() throws Exception {
        mockMvc.perform(delete("/api/publishers/{id}", publisher.getId())
                        .header("Authorization", "Bearer " + jwtToken)
                        .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void testGetAllPublishers() throws Exception {
        when(publisherService.getAllPublishers()).thenReturn(Collections.singletonList(publisher));

        mockMvc.perform(get("/api/publishers")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Publisher"));
    }
}