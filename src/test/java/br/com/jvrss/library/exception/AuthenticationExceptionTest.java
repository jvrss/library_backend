package br.com.jvrss.library.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthenticationExceptionTest {

    @Test
    void testAuthenticationExceptionMessage() {
        String errorMessage = "Authentication failed";
        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            throw new AuthenticationException(errorMessage);
        });
        assertEquals(errorMessage, exception.getMessage());
    }
}