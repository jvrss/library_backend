package br.com.jvrss.library.login.service.impl;

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

@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Autowired
    public LoginServiceImpl(LoginRepository loginRepository, @Lazy PasswordEncoder passwordEncoder,
                            @Lazy AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.loginRepository = loginRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Login createLogin(Login login) {
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        return loginRepository.save(login);
    }

    @Override
    public Login getLoginById(UUID id) {
        Optional<Login> login = loginRepository.findById(id);
        return login.orElse(null);
    }

    @Override
    public List<Login> getAllLogins() {
        return loginRepository.findAll();
    }

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

    @Override
    public void deleteLogin(UUID id) {
        loginRepository.deleteById(id);
    }

    public String authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final UserDetails userDetails = loadUserByUsername(username);
        return jwtUtil.generateToken(userDetails.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Login login = loginRepository.findByEmail(email);
        if (login == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(login.getEmail(), login.getPassword(), new ArrayList<>());
    }
}