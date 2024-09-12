// src/test/java/br/com/jvrss/library/util/RandomStringGeneratorTest.java
package br.com.jvrss.library.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomStringGeneratorTest {

    @Test
    public void testGenerate() {
        int length = 10;
        String randomString = RandomStringGenerator.generate(length);

        assertNotNull(randomString, "Generated string should not be null");
        assertEquals(length, randomString.length(), "Generated string should have the correct length");

        for (char c : randomString.toCharArray()) {
            assertTrue(RandomStringGenerator.CHARACTERS.indexOf(c) >= 0, "Generated string should only contain valid characters");
        }
    }
}