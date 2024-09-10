package br.com.jvrss.library.loan.service.impl;

import br.com.jvrss.library.loan.model.Loan;
import br.com.jvrss.library.loan.repository.LoanRepository;
import br.com.jvrss.library.loan.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateLoan() {
        Loan loan = new Loan();
        loan.setId(UUID.randomUUID());
        loan.setDate(LocalDateTime.now());

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan createdLoan = loanService.createLoan(loan);

        assertThat(createdLoan).isNotNull();
        assertThat(createdLoan.getDate()).isEqualTo(loan.getDate());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void testGetLoanById() {
        UUID id = UUID.randomUUID();
        Loan loan = new Loan();
        loan.setId(id);
        loan.setDate(LocalDateTime.now());

        when(loanRepository.findById(id)).thenReturn(Optional.of(loan));

        Loan foundLoan = loanService.getLoanById(id);

        assertThat(foundLoan).isNotNull();
        assertThat(foundLoan.getDate()).isEqualTo(loan.getDate());
        verify(loanRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllLoans() {
        Loan loan = new Loan();
        loan.setId(UUID.randomUUID());
        loan.setDate(LocalDateTime.now());
        List<Loan> loans = Collections.singletonList(loan);

        when(loanRepository.findAll()).thenReturn(loans);

        List<Loan> allLoans = loanService.getAllLoans();

        assertThat(allLoans).isNotNull();
        assertThat(allLoans).hasSize(1);
        assertThat(allLoans.get(0).getDate()).isEqualTo(loan.getDate());
        verify(loanRepository, times(1)).findAll();
    }

    @Test
    void testUpdateLoan() {
        UUID id = UUID.randomUUID();
        Loan loan = new Loan();
        loan.setId(id);
        loan.setDate(LocalDateTime.now());

        when(loanRepository.existsById(id)).thenReturn(true);
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan updatedLoan = loanService.updateLoan(id, loan);

        assertThat(updatedLoan).isNotNull();
        assertThat(updatedLoan.getDate()).isEqualTo(loan.getDate());
        verify(loanRepository, times(1)).existsById(id);
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void testDeleteLoan() {
        UUID id = UUID.randomUUID();

        doNothing().when(loanRepository).deleteById(id);

        loanService.deleteLoan(id);

        verify(loanRepository, times(1)).deleteById(id);
    }

    @Test
    void testUpdateReturnedTime() {
        UUID id = UUID.randomUUID();
        Loan loan = new Loan();
        loan.setId(id);
        loan.setDate(LocalDateTime.now());

        when(loanRepository.findById(id)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        Loan updatedLoan = loanService.updateReturnedTime(id);

        assertThat(updatedLoan).isNotNull();
        assertThat(updatedLoan.getReturned()).isNotNull();
        verify(loanRepository, times(1)).findById(id);
        verify(loanRepository, times(1)).save(loan);
    }
}