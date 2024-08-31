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

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan createdLoan = loanService.createLoan(loan);

        if (!isLoanCreationTimeValid(loan.getDate())) {
            throw new IllegalArgumentException("Loan creation time is not within the allowed 5 minutes tolerance.");
        }

        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable UUID id) {
        Loan loan = loanService.getLoanById(id);
        if (loan != null) {
            return new ResponseEntity<>(loan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable UUID id, @RequestBody Loan loan) {
        Loan updatedLoan = loanService.updateLoan(id, loan);
        if (updatedLoan != null) {
            return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable UUID id) {
        loanService.deleteLoan(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

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