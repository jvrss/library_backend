package br.com.jvrss.library.login.repository;

import br.com.jvrss.library.login.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginRepository extends JpaRepository<Login, UUID> {
}