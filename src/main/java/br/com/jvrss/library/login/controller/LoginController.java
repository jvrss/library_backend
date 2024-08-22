package br.com.jvrss.library.login.controller;

import br.com.jvrss.library.login.dto.LoginDto;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/logins")
public class LoginController {

    @Autowired
    @Lazy
    private LoginService loginService;

    @PostMapping
    public ResponseEntity<LoginDto> createLogin(@Valid @RequestBody Login login) {
        Login createdLogin = loginService.createLogin(login);
        return ResponseEntity.ok(convertToDto(createdLogin));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginDto> getLoginById(@PathVariable UUID id) {
        Login login = loginService.getLoginById(id);
        if (login != null) {
            return ResponseEntity.ok(convertToDto(login));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<LoginDto>> getAllLogins() {
        List<Login> logins = loginService.getAllLogins();
        List<LoginDto> loginDtos = logins.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(loginDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoginDto> updateLogin(@PathVariable UUID id, @Valid @RequestBody Login login) {
        Login updatedLogin = loginService.updateLogin(id, login);
        if (updatedLogin != null) {
            return ResponseEntity.ok(convertToDto(updatedLogin));
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
        String token = loginService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    private LoginDto convertToDto(Login login) {
        return new LoginDto(login.getId(), login.getName(), login.getEmail());
    }
}