package br.com.jvrss.library.login.service;

import br.com.jvrss.library.login.model.Login;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoginService {
    Login createLogin(Login login);
    Optional<Login> getLoginById(UUID id);
    List<Login> getAllLogins();
    Login updateLogin(UUID id, Login login);
    void deleteLogin(UUID id);
    String authenticate(String username, String password);
}