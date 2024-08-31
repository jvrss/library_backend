package br.com.jvrss.library.user.service.impl;

import br.com.jvrss.library.user.model.User;
import br.com.jvrss.library.user.repository.UserRepository;
import br.com.jvrss.library.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the UserService interface for handling user-related operations.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their CPF.
     *
     * @param cpf the CPF of the user
     * @return the user if found, or null if not found
     */
    @Override
    public User getUserByCpf(String cpf) {
        return userRepository.findById(cpf).orElse(null);
    }

    /**
     * Updates an existing user.
     *
     * @param cpf the CPF of the user to update
     * @param user the updated user data
     * @return the updated user if found, or null if not found
     */
    @Override
    public User updateUser(String cpf, User user) {
        if (userRepository.existsById(cpf)) {
            user.setCpf(cpf);
            return userRepository.save(user);
        }
        return null;
    }

    /**
     * Deletes a user by their CPF.
     *
     * @param cpf the CPF of the user to delete
     */
    @Override
    public void deleteUser(String cpf) {
        userRepository.deleteById(cpf);
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}