// src/main/java/br/com/jvrss/library/user/service/impl/UserServiceImpl.java
package br.com.jvrss.library.user.service.impl;

import br.com.jvrss.library.user.model.User;
import br.com.jvrss.library.user.repository.UserRepository;
import br.com.jvrss.library.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByCpf(String cpf) {
        return userRepository.findById(cpf).orElse(null);
    }

    @Override
    public User updateUser(String cpf, User user) {
        if (userRepository.existsById(cpf)) {
            user.setCpf(cpf);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public void deleteUser(String cpf) {
        userRepository.deleteById(cpf);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}