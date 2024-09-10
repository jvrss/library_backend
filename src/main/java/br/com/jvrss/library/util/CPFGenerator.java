package br.com.jvrss.library.util;

import java.util.Random;

public class CPFGenerator {

    public static String generateCPF() {
        Random random = new Random();
        int[] cpf = new int[11];

        // Generate the first nine digits
        for (int i = 0; i < 9; i++) {
            cpf[i] = random.nextInt(10);
        }

        // Calculate the first verification digit
        cpf[9] = calculateVerificationDigit(cpf, 10);

        // Calculate the second verification digit
        cpf[10] = calculateVerificationDigit(cpf, 11);

        // Convert the array to a string
        StringBuilder cpfString = new StringBuilder();
        for (int digit : cpf) {
            cpfString.append(digit);
        }

        return cpfString.toString();
    }

    private static int calculateVerificationDigit(int[] cpf, int weight) {
        int sum = 0;
        for (int i = 0; i < weight - 1; i++) {
            sum += cpf[i] * (weight - i);
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}