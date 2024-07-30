package br.com.jvrss.library.login.service.impl;

import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.repository.LoginRepository;
import br.com.jvrss.library.login.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginRepository loginRepository;

    @Override
    public Login createLogin(Login login) {
        return loginRepository.save(login);
    }

    @Override
    public Login getLoginById(UUID id) {
        Optional<Login> login = loginRepository.findById(id);
        return login.orElse(null);
    }

    @Override
    public List<Login> getAllLogins() {
        return loginRepository.findAll();
    }

    @Override
    public Login updateLogin(UUID id, Login login) {
        if (loginRepository.existsById(id)) {
            login.setId(id);
            return loginRepository.save(login);
        }
        return null;
    }

    @Override
    public void deleteLogin(UUID id) {
        loginRepository.deleteById(id);
    }
}