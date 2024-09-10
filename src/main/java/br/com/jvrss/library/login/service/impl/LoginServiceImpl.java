package br.com.jvrss.library.login.service.impl;

import br.com.jvrss.library.exception.AuthenticationException;
import br.com.jvrss.library.login.model.Login;
import br.com.jvrss.library.login.repository.LoginRepository;
import br.com.jvrss.library.login.service.LoginService;
import br.com.jvrss.library.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.jvrss.library.util.Messages.INVALID_EMAIL_OR_PASSWORD;

/**
 * Implementation of the LoginService interface and UserDetailsService for handling login-related operations.
 */
@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Constructs a new LoginServiceImpl with the specified dependencies.
     *
     * @param loginRepository the login repository
     * @param passwordEncoder the password encoder
     * @param authenticationManager the authentication manager
     * @param jwtUtil the JWT utility
     */
    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository, @Lazy PasswordEncoder passwordEncoder,
                            @Lazy AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Creates a new login.
     *
     * @param login the login to create
     * @return the created login
     */
    @Override
    public Login createLogin(Login login) {
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        return loginRepository.save(login);
    }

    /**
     * Retrieves a login by its ID.
     *
     * @param id the ID of the login
     * @return the login if found, or null if not found
     */
    @Override
    public Optional<Login> getLoginById(UUID id) {
        return loginRepository.findById(id);
    }

    /**
     * Retrieves all logins.
     *
     * @return a list of all logins
     */
    @Override
    public List<Login> getAllLogins() {
        return loginRepository.findAll();
    }

    /**
     * Updates an existing login.
     *
     * @param id the ID of the login to update
     * @param login the updated login data
     * @return the updated login if found, or null if not found
     */
    @Override
    public Login updateLogin(UUID id, Login login) {
        if (loginRepository.existsById(id)) {
            login.setId(id);
            if (!login.getPassword().startsWith("$2a$")) {
                login.setPassword(passwordEncoder.encode(login.getPassword()));
            }
            return loginRepository.save(login);
        }
        return null;
    }

    /**
     * Deletes a login by its ID.
     *
     * @param id the ID of the login to delete
     */
    @Override
    public void deleteLogin(UUID id) {
        loginRepository.deleteById(id);
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param email the email of the user
     * @param password the password of the user
     * @return the JWT token if authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public String authenticate(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (Exception e) {
            throw new AuthenticationException(INVALID_EMAIL_OR_PASSWORD);
        }
        final UserDetails userDetails = loadUserByEmail(email);
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AuthenticationException(INVALID_EMAIL_OR_PASSWORD);
        }
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    /**
     * Loads a user by their username.
     *
     * @param username the username of the user
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadUserByEmail(username);
    }

    /**
     * Loads a user by their email.
     *
     * @param email the email of the user
     * @return the user details
     * @throws UsernameNotFoundException if the user is not found
     */
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Login login = loginRepository.findByEmail(email);
        if (login == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(login.getEmail(), login.getPassword(), new ArrayList<>());
    }
}