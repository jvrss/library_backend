package br.com.jvrss.library.login.repository;

import br.com.jvrss.library.login.model.Login;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LoginRepositoryTest {

    @Autowired
    private LoginRepository loginRepository;

    @Test
    void testSaveLogin() {
        Login login = new Login();
        login.setId(UUID.randomUUID());
        login.setName("johndoe");
        login.setEmail("johndoe@example.com");
        login.setPassword("password");

        Login savedLogin = loginRepository.save(login);

        assertThat(savedLogin).isNotNull();
        assertThat(savedLogin.getId()).isEqualTo(login.getId());
    }

    @Test
    void testFindById() {
        UUID id = UUID.randomUUID();
        Login login = new Login();
        login.setId(id);
        login.setName("johndoe");
        login.setEmail("johndoe@example.com");
        login.setPassword("password");

        loginRepository.save(login);

        Optional<Login> foundLogin = loginRepository.findById(id);

        assertThat(foundLogin).isPresent();
        assertThat(foundLogin.get().getName()).isEqualTo("johndoe");
    }

    @Test
    void testDeleteLogin() {
        UUID id = UUID.randomUUID();
        Login login = new Login();
        login.setId(id);
        login.setName("johndoe");
        login.setEmail("johndoe@example.com");
        login.setPassword("password");

        loginRepository.save(login);
        loginRepository.deleteById(id);

        Optional<Login> foundLogin = loginRepository.findById(id);

        assertThat(foundLogin).isNotPresent();
    }
}