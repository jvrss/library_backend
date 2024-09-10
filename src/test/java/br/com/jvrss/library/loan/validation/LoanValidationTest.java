package br.com.jvrss.library.loan.validation;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class LoanValidationTest {

    @Test
    void testIsLoanCreationTimeValidWithinRange() {
        LocalDateTime now = LocalDateTime.now();
        assertThat(LoanValidation.isLoanCreationTimeValid(now)).isTrue();
    }

    @Test
    void testIsLoanCreationTimeValidOutsideRange() {
        LocalDateTime past = LocalDateTime.now().minusMinutes(10);
        assertThat(LoanValidation.isLoanCreationTimeValid(past)).isFalse();
    }

    @Test
    void testIsLoanCreationTimeValidFutureWithinRange() {
        LocalDateTime future = LocalDateTime.now().plusMinutes(3);
        assertThat(LoanValidation.isLoanCreationTimeValid(future)).isTrue();
    }

    @Test
    void testIsLoanCreationTimeValidFutureOutsideRange() {
        LocalDateTime future = LocalDateTime.now().plusMinutes(10);
        assertThat(LoanValidation.isLoanCreationTimeValid(future)).isFalse();
    }
}