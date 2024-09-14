package br.com.jvrss.library.login.service;

import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.repository.LoginRepository;
import br.com.jvrss.library.login.service.impl.LoginServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServiceImplTest {

    @Mock
    private LoginRepository loginRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private LoginServiceImpl loginService;

    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.setId(UUID.randomUUID());
        login.setName("johndoe");
        login.setEmail("johndoe@example.com");
        login.setPassword("password");
    }

    @Test
    void testCreateLogin() {
        when(passwordEncoder.encode(login.getPassword())).thenReturn("$2a$password");
        when(loginRepository.save(any(Login.class))).thenReturn(login);

        Login createdLogin = loginService.createLogin(login);

        assertThat(createdLogin).isNotNull();
        assertThat(createdLogin.getName()).isEqualTo("johndoe");
        verify(loginRepository, times(1)).save(login);
    }

    @Test
    void testGetLoginById() {
        when(loginRepository.findById(login.getId())).thenReturn(Optional.of(login));

        Optional<Login> foundLogin = loginService.getLoginById(login.getId());

        assertThat(foundLogin).isPresent();
        assertThat(foundLogin.get().getName()).isEqualTo("johndoe");
        verify(loginRepository, times(1)).findById(login.getId());
    }

    @Test
    void testUpdateLogin() {
        when(loginRepository.existsById(login.getId())).thenReturn(true);
        when(passwordEncoder.encode(login.getPassword())).thenReturn("$2a$password");
        when(loginRepository.save(any(Login.class))).thenReturn(login);

        Login updatedLogin = loginService.updateLogin(login.getId(), login);

        assertThat(updatedLogin).isNotNull();
        assertThat(updatedLogin.getName()).isEqualTo("johndoe");
        verify(loginRepository, times(1)).save(login);
    }

    @Test
    void testDeleteLogin() {
        doNothing().when(loginRepository).deleteById(login.getId());

        loginService.deleteLogin(login.getId());

        verify(loginRepository, times(1)).deleteById(login.getId());
    }
}