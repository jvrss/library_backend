package br.com.jvrss.library.user.service.impl;

import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.user.model.User;
import br.com.jvrss.library.user.repository.UserRepository;
import br.com.jvrss.library.util.CPFGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
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
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getCpf()).isEqualTo(user.getCpf());
        assertThat(createdUser.getLogin().getId()).isEqualTo(user.getLogin().getId());
        assertThat(createdUser.getLogin().getName()).isEqualTo(user.getLogin().getName());
        assertThat(createdUser.getLogin().getEmail()).isEqualTo(user.getLogin().getEmail());
        assertThat(createdUser.getLogin().getPassword()).isEqualTo(user.getLogin().getPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(user.getCpf())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserByCpf(user.getCpf());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getCpf()).isEqualTo(user.getCpf());
        assertThat(foundUser.get().getLogin().getId()).isEqualTo(user.getLogin().getId());
        assertThat(foundUser.get().getLogin().getName()).isEqualTo(user.getLogin().getName());
        assertThat(foundUser.get().getLogin().getEmail()).isEqualTo(user.getLogin().getEmail());
        assertThat(foundUser.get().getLogin().getPassword()).isEqualTo(user.getLogin().getPassword());
        verify(userRepository, times(1)).findById(user.getCpf());
    }

    @Test
    void testUpdateUser() {
        when(userRepository.existsById(user.getCpf())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);

        user.getLogin().setName("janedoe");
        user.getLogin().setEmail("janedoe@example.com");
        user.getLogin().setPassword("newpassword");

        User updatedUser = userService.updateUser(user.getCpf(), user);

        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getLogin().getName()).isEqualTo("janedoe");
        assertThat(updatedUser.getLogin().getEmail()).isEqualTo("janedoe@example.com");
        assertThat(updatedUser.getLogin().getPassword()).isEqualTo("newpassword");
        verify(userRepository, times(1)).existsById(user.getCpf());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById(user.getCpf());

        userService.deleteUser(user.getCpf());

        verify(userRepository, times(1)).deleteById(user.getCpf());
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        Iterable<User> users = userService.getAllUsers();

        assertThat(users).hasSize(1);
        verify(userRepository, times(1)).findAll();
    }
}