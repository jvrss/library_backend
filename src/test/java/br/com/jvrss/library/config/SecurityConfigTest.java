package br.com.jvrss.library.config;

import br.com.jvrss.library.filter.JwtRequestFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class SecurityConfigTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtRequestFilter jwtRequestFilter;

    @InjectMocks
    private SecurityConfig securityConfig;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testPublicEndpoints() throws Exception {
        mockMvc.perform(get("/api/logins/authenticate"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/csrf-token"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();
        String encodedPassword = passwordEncoder.encode("password");
        assert(passwordEncoder.matches("password", encodedPassword));
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        HttpSecurity http = mock(HttpSecurity.class);
        AuthenticationManager authManager = securityConfig.authenticationManager(http);
        assert(authManager != null);
    }
}