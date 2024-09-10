package br.com.jvrss.library.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CPFGeneratorTest {

    @Test
    public void testGenerateCPF() {
        String cpf = CPFGenerator.generateCPF();
        assertNotNull(cpf);
        assertEquals(11, cpf.length());
        assertTrue(cpf.matches("\\d{11}"));
    }
}