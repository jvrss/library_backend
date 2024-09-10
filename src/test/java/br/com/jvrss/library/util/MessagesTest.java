package br.com.jvrss.library.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessagesTest {

    @Test
    public void testInvalidEmailOrPasswordMessage() {
        assertEquals("Invalid email or password", Messages.INVALID_EMAIL_OR_PASSWORD);
    }
}