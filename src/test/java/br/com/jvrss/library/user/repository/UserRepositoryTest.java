package br.com.jvrss.library.user.repository;

import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.user.model.User;
import br.com.jvrss.library.util.CPFGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

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

        userRepository.save(user);
    }

    @Test
    void testSaveUser() {
        User savedUser = userRepository.save(user);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getCpf()).isEqualTo(user.getCpf());
    }

    @Test
    void testFindById() {
        Optional<User> foundUser = userRepository.findById(user.getCpf());
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getCpf()).isEqualTo(user.getCpf());
        assertThat(foundUser.get().getLogin().getId()).isEqualTo(user.getLogin().getId());
        assertThat(foundUser.get().getLogin().getName()).isEqualTo(user.getLogin().getName());
        assertThat(foundUser.get().getLogin().getEmail()).isEqualTo(user.getLogin().getEmail());
        assertThat(foundUser.get().getLogin().getPassword()).isEqualTo(user.getLogin().getPassword());
    }

    @Test
    void testFindAll() {
        Iterable<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
    }

    @Test
    void testDeleteById() {
        userRepository.deleteById(user.getCpf());
        Optional<User> deletedUser = userRepository.findById(user.getCpf());
        assertThat(deletedUser).isNotPresent();
    }
}