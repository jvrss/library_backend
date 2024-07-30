// src/main/java/br/com/jvrss/library/user/controller/UserController.java
package br.com.jvrss.library.user.controller;

import br.com.jvrss.library.user.model.User;
import br.com.jvrss.library.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{cpf}")
    public User getUserByCpf(@PathVariable String cpf) {
        return userService.getUserByCpf(cpf);
    }

    @PutMapping("/{cpf}")
    public User updateUser(@PathVariable String cpf, @Valid @RequestBody User user) {
        return userService.updateUser(cpf, user);
    }

    @DeleteMapping("/{cpf}")
    public void deleteUser(@PathVariable String cpf) {
        userService.deleteUser(cpf);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}