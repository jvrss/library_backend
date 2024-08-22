package br.com.jvrss.library.login.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    @Size(min = 7, max = 200)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and include a digit, a lower case letter, an upper case letter, a special character, and no whitespace"
    )
    private String password;

}