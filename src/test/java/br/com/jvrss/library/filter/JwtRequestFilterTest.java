package br.com.jvrss.library.filter;

import br.com.jvrss.library.util.JwtUtil;
import io.jsonwebtoken.UnsupportedJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class JwtRequestFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private UserDetailsService userDetailsService;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilter(jwtRequestFilter)
                .build();
    }

    @Test
    void testFilterBypassForAuthenticateEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/logins/authenticate"))
                .andExpect(status().isOk());
    }

    @Test
    void testFilterWithValidJwt() throws Exception {
        String token = "valid-jwt-token";
        String username = "user";

        when(jwtUtil.extractUsername(token)).thenReturn(username);
        when(jwtUtil.validateToken(token, username)).thenReturn(true);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);
        when(userDetailsService.loadUserByUsername(username)).thenReturn(userDetails);

        mockMvc.perform(MockMvcRequestBuilders.get("/some-secured-endpoint")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    void testFilterWithInvalidJwt() throws Exception {
        String token = "invalid-jwt-token";

        when(jwtUtil.extractUsername(token)).thenThrow(new UnsupportedJwtException("Invalid JWT token"));

        mockMvc.perform(MockMvcRequestBuilders.get("/some-secured-endpoint")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnauthorized());
    }
}