// src/main/java/br/com/jvrss/library/user/repository/UserRepository.java
package br.com.jvrss.library.user.repository;

import br.com.jvrss.library.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}