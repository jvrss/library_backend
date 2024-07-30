// src/main/java/br/com/jvrss/library/user/service/UserService.java
package br.com.jvrss.library.user.service;

import br.com.jvrss.library.user.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserByCpf(String cpf);
    User updateUser(String cpf, User user);
    void deleteUser(String cpf);
    List<User> getAllUsers();
}