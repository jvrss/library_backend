package br.com.jvrss.library.loan.repository;

import br.com.jvrss.library.loan.model.Loan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Test
    void testSaveLoan() {
        Loan loan = new Loan();
        loan.setDate(LocalDateTime.now());

        Loan savedLoan = loanRepository.save(loan);

        assertThat(savedLoan).isNotNull();
        assertThat(savedLoan.getDate()).isEqualTo(loan.getDate());
    }

    @Test
    void testFindById() {
        Loan loan = new Loan();
        loan.setDate(LocalDateTime.now());

        loan = loanRepository.save(loan);

        Optional<Loan> foundLoan = loanRepository.findById(loan.getId());

        assertThat(foundLoan).isPresent();
        assertThat(foundLoan.get().getDate()).isEqualTo(loan.getDate());
    }

    @Test
    void testFindAll() {
        Loan loan1 = new Loan();
        loan1.setId(UUID.randomUUID());
        loan1.setDate(LocalDateTime.now());

        Loan loan2 = new Loan();
        loan2.setId(UUID.randomUUID());
        loan2.setDate(LocalDateTime.now().plusDays(1));

        loanRepository.save(loan1);
        loanRepository.save(loan2);

        Iterable<Loan> loans = loanRepository.findAll();

        assertThat(loans).hasSize(2);
    }

    @Test
    void testDeleteById() {
        UUID id = UUID.randomUUID();
        Loan loan = new Loan();
        loan.setId(id);
        loan.setDate(LocalDateTime.now());

        loanRepository.save(loan);
        loanRepository.deleteById(id);

        Optional<Loan> deletedLoan = loanRepository.findById(id);

        assertThat(deletedLoan).isNotPresent();
    }
}