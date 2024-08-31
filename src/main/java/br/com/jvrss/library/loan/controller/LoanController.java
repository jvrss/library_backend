package br.com.jvrss.library.loan.controller;

import br.com.jvrss.library.loan.model.Loan;
import br.com.jvrss.library.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static br.com.jvrss.library.loan.validation.LoanValidation.isLoanCreationTimeValid;

/**
 * REST controller for handling loan-related requests.
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    /**
     * Creates a new loan.
     *
     * @param loan the loan to create
     * @return the created loan
     */
    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan createdLoan = loanService.createLoan(loan);

        if (!isLoanCreationTimeValid(loan.getDate())) {
            throw new IllegalArgumentException("Loan creation time is not within the allowed 5 minutes tolerance.");
        }

        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    /**
     * Retrieves a loan by its ID.
     *
     * @param id the ID of the loan
     * @return the loan if found, or a 404 Not Found response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable UUID id) {
        Loan loan = loanService.getLoanById(id);
        if (loan != null) {
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all loans.
     *
     * @return a list of all loans
     */
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    /**
     * Updates an existing loan.
     *
     * @param id the ID of the loan to update
     * @param loan the updated loan data
     * @return the updated loan if found, or a 404 Not Found response if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable UUID id, @RequestBody Loan loan) {
        Loan updatedLoan = loanService.updateLoan(id, loan);
        if (updatedLoan != null) {
            return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Deletes a loan by its ID.
     *
     * @param id the ID of the loan to delete
     * @return a 204 No Content response
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable UUID id) {
        loanService.deleteLoan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Updates the returned time of a loan.
     *
     * @param id the ID of the loan to update
     * @return the updated loan if found, or a 404 Not Found response if not found
     */
    @PutMapping("/{id}/return")
    public ResponseEntity<Loan> updateReturnedTime(@PathVariable UUID id) {
        Loan updatedLoan = loanService.updateReturnedTime(id);
        if (updatedLoan != null) {
            return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}