package br.com.jvrss.library.loan.validation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class LoanValidation {

    public static boolean isLoanCreationTimeValid(LocalDateTime loanDate) {
        LocalDateTime now = LocalDateTime.now();
        long minutesDifference = ChronoUnit.MINUTES.between(loanDate, now);
        return Math.abs(minutesDifference) <= 5;
    }

}
