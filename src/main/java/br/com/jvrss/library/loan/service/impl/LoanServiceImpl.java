package br.com.jvrss.library.loan.service.impl;

import br.com.jvrss.library.loan.model.Loan;
import br.com.jvrss.library.loan.repository.LoanRepository;
import br.com.jvrss.library.loan.service.LoanService;
import br.com.jvrss.library.loan.validation.LoanValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public Loan createLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan getLoanById(UUID id) {
        Optional<Loan> loan = loanRepository.findById(id);
        return loan.orElse(null);
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan updateLoan(UUID id, Loan loan) {
        if (loanRepository.existsById(id)) {
            loan.setId(id);
            return loanRepository.save(loan);
        }
        return null;
    }

    @Override
    public void deleteLoan(UUID id) {
        loanRepository.deleteById(id);
    }

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