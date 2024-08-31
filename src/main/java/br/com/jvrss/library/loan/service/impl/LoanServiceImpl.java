// src/main/java/br/com/jvrss/library/loan/service/impl/LoanServiceImpl.java
package br.com.jvrss.library.loan.service.impl;

import br.com.jvrss.library.loan.model.Loan;
import br.com.jvrss.library.loan.repository.LoanRepository;
import br.com.jvrss.library.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the LoanService interface.
 */
@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    /**
     * Creates a new loan.
     *
     * @param loan the loan to create
     * @return the created loan
     */
    @Override
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    /**
     * Retrieves a loan by its ID.
     *
     * @param id the ID of the loan
     * @return the loan, or null if not found
     */
    @Override
    public Loan getLoanById(UUID id) {
        Optional<Loan> loan = loanRepository.findById(id);
        return loan.orElse(null);
    }

    /**
     * Retrieves all loans.
     *
     * @return a list of all loans
     */
    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    /**
     * Updates an existing loan.
     *
     * @param id the ID of the loan to update
     * @param loan the updated loan data
     * @return the updated loan, or null if not found
     */
    @Override
    public Loan updateLoan(UUID id, Loan loan) {
        if (loanRepository.existsById(id)) {
            loan.setId(id);
            return loanRepository.save(loan);
        }
        return null;
    }

    /**
     * Deletes a loan by its ID.
     *
     * @param id the ID of the loan to delete
     */
    @Override
    public void deleteLoan(UUID id) {
        loanRepository.deleteById(id);
    }

    /**
     * Updates the returned time of a loan.
     *
     * @param id the ID of the loan to update
     * @return the updated loan, or null if not found
     */
    @Override
    public Loan updateReturnedTime(UUID id) {
        Loan loan = loanRepository.findById(id).orElse(null);
        if (loan != null) {
            loan.setReturned(LocalDateTime.now());
            loanRepository.save(loan);
        }
        return loan;
    }
}