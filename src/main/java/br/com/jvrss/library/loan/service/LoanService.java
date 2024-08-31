package br.com.jvrss.library.loan.service;

import br.com.jvrss.library.loan.model.Loan;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface LoanService {
    Loan createLoan(Loan loan);
    Loan getLoanById(UUID id);
    List<Loan> getAllLoans();
    Loan updateLoan(UUID id, Loan loan);
    void deleteLoan(UUID id);
    Loan updateReturnedTime(UUID id);
}