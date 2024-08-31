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

/**
 * REST controller for handling login-related requests.
 */
@RestController
@RequestMapping("/api/logins")
public class LoginController {

    @Autowired
    @Lazy
    private LoginService loginService;

    /**
     * Creates a new login.
     *
     * @param login the login to create
     * @return the created login
     */
    @PostMapping
    public ResponseEntity<LoginDto> createLogin(@Valid @RequestBody Login login) {
        Login createdLogin = loginService.createLogin(login);
        return ResponseEntity.ok(convertToDto(createdLogin));
    }

    /**
     * Retrieves a login by its ID.
     *
     * @param id the ID of the login
     * @return the login if found, or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<LoginDto> getLoginById(@PathVariable UUID id) {
        Login login = loginService.getLoginById(id);
        if (login != null) {
            return ResponseEntity.ok(convertToDto(login));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all logins.
     *
     * @return a list of all logins
     */
    @GetMapping
    public ResponseEntity<List<LoginDto>> getAllLogins() {
        List<Login> logins = loginService.getAllLogins();
        List<LoginDto> loginDtos = logins.stream().map(this::convertToDto).collect(Collectors.toList());
        return ResponseEntity.ok(loginDtos);
    }

    /**
     * Updates an existing login.
     *
     * @param id the ID of the login to update
     * @param login the updated login data
     * @return the updated login if found, or a 404 Not Found response if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<LoginDto> updateLogin(@PathVariable UUID id, @Valid @RequestBody Login login) {
        Login updatedLogin = loginService.updateLogin(id, login);
        if (updatedLogin != null) {
            return ResponseEntity.ok(convertToDto(updatedLogin));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a login by its ID.
     *
     * @param id the ID of the login to delete
     * @return a 204 No Content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogin(@PathVariable UUID id) {
        loginService.deleteLogin(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param authenticationRequest the authentication request containing email and password
     * @return the JWT token if authentication is successful
     */
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        String token = loginService.authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        return ResponseEntity.ok(token);
    }

    /**
     * Converts a Login entity to a LoginDto.
     *
     * @param login the login entity
     * @return the login DTO
     */
    private LoginDto convertToDto(Login login) {
        return new LoginDto(login.getId(), login.getName(), login.getEmail());
    }
}