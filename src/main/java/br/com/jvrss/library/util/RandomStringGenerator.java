// src/test/java/br/com/jvrss/library/util/RandomStringGenerator.java
package br.com.jvrss.library.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomStringGenerator {
    protected static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Random RANDOM = new SecureRandom();

    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}