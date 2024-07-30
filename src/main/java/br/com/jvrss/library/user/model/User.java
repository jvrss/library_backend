// src/main/java/br/com/jvrss/library/user/model/User.java
package br.com.jvrss.library.user.model;

import br.com.jvrss.library.login.model.Login;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Pattern(regexp = "\\d{11}", message = "CPF must be 11 digits")
    @Column(length = 11, unique = true)
    private String cpf;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "login_id", referencedColumnName = "id")
    private Login login;
}