package br.com.jvrss.library.login.controller;

import br.com.jvrss.library.login.model.AuthenticationRequest;
import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/logins")
public class LoginController {

    @Autowired
    @Lazy
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<Login> createLogin(@Valid @RequestBody Login login) {
        Login createdLogin = loginService.createLogin(login);
        return ResponseEntity.ok(createdLogin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Login> getLoginById(@PathVariable UUID id) {
        Login login = loginService.getLoginById(id);
        if (login != null) {
            return ResponseEntity.ok(login);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Login>> getAllLogins() {
        List<Login> logins = loginService.getAllLogins();
        return ResponseEntity.ok(logins);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Login> updateLogin(@PathVariable UUID id, @Valid @RequestBody Login login) {
        Login updatedLogin = loginService.updateLogin(id, login);
        if (updatedLogin != null) {
            return ResponseEntity.ok(updatedLogin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable UUID id) {
        loginService.deleteLogin(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        String token = loginService.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        return ResponseEntity.ok(token);
    }
}