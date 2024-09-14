package br.com.jvrss.library.filter;

import br.com.jvrss.library.author.controller.AuthorController;
import br.com.jvrss.library.author.service.AuthorService;
import br.com.jvrss.library.book.controller.BookController;
import br.com.jvrss.library.book.service.BookService;
import br.com.jvrss.library.employee.controller.EmployeeController;
import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.employee.service.EmployeeService;
import br.com.jvrss.library.language.controller.LanguageController;
import br.com.jvrss.library.language.service.LanguageService;
import br.com.jvrss.library.loan.controller.LoanController;
import br.com.jvrss.library.loan.service.LoanService;
import br.com.jvrss.library.login.model.AuthenticationRequest;
import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.service.LoginService;
import br.com.jvrss.library.publisher.controller.PublisherController;
import br.com.jvrss.library.publisher.service.PublisherService;
import br.com.jvrss.library.user.controller.UserController;
import br.com.jvrss.library.user.service.UserService;
import br.com.jvrss.library.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @MockBean
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @MockBean
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private LoginService loginService;

    @InjectMocks
    private EmployeeController employeeController;

    @MockBean
    private LanguageService languageService;

    @InjectMocks
    private LanguageController languageController;

    @MockBean
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @MockBean
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

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
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setEmail("test@example.com");
        authRequest.setPassword("Password@123");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/logins/authenticate")
                .contentType("application/json")
                .content(new ObjectMapper().writeValueAsString(authRequest)))
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

        mockMvc.perform(MockMvcRequestBuilders.get("/api/logins")
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