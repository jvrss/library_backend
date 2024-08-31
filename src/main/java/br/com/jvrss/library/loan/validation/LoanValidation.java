// src/main/java/br/com/jvrss/library/loan/validation/LoanValidation.java
package br.com.jvrss.library.loan.validation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for loan validation.
 */
public class LoanValidation {

    /**
     * Validates if the loan creation time is within a valid range.
     *
     * @param loanDate the date and time of the loan creation
     * @return true if the loan creation time is valid, false otherwise
     */
    public static boolean isLoanCreationTimeValid(LocalDateTime loanDate) {
        LocalDateTime now = LocalDateTime.now();
        long minutesDifference = ChronoUnit.MINUTES.between(loanDate, now);
        return Math.abs(minutesDifference) <= 5;
    }
}