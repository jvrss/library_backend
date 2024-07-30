package br.com.jvrss.library.login.service;

import br.com.jvrss.library.login.model.Login;

import java.util.List;
import java.util.UUID;

public interface LoginService {
    Login createLogin(Login login);
    Login getLoginById(UUID id);
    List<Login> getAllLogins();
    Login updateLogin(UUID id, Login login);
    void deleteLogin(UUID id);
}