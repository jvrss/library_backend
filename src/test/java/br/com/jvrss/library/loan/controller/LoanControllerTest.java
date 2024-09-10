package br.com.jvrss.library.loan.controller;

import br.com.jvrss.library.loan.model.Loan;
import br.com.jvrss.library.loan.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LoanControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @Test
    void testCreateLoan() throws Exception {
        Loan loan = new Loan();
        loan.setId(UUID.randomUUID());
        loan.setDate(LocalDateTime.now());

        when(loanService.createLoan(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\": \"" + loan.getDate().toString() + "\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.date").value(loan.getDate().toString()));
    }

    @Test
    void testGetLoanById() throws Exception {
        UUID id = UUID.randomUUID();
        Loan loan = new Loan();
        loan.setId(id);
        loan.setDate(LocalDateTime.now());

        when(loanService.getLoanById(id)).thenReturn(loan);

        mockMvc.perform(get("/api/loans/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(loan.getDate().toString()));
    }

    @Test
    void testGetAllLoans() throws Exception {
        Loan loan = new Loan();
        loan.setId(UUID.randomUUID());
        loan.setDate(LocalDateTime.now());
        List<Loan> loans = Collections.singletonList(loan);

        when(loanService.getAllLoans()).thenReturn(loans);

        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date").value(loan.getDate().toString()));
    }

    @Test
    void testUpdateLoan() throws Exception {
        UUID id = UUID.randomUUID();
        Loan loan = new Loan();
        loan.setId(id);
        loan.setDate(LocalDateTime.now());

        when(loanService.updateLoan(any(UUID.class), any(Loan.class))).thenReturn(loan);

        mockMvc.perform(put("/api/loans/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"date\": \"" + loan.getDate().toString() + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(loan.getDate().toString()));
    }

    @Test
    void testDeleteLoan() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/loans/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateReturnedTime() throws Exception {
        UUID id = UUID.randomUUID();
        Loan loan = new Loan();
        loan.setId(id);
        loan.setDate(LocalDateTime.now());

        when(loanService.updateReturnedTime(id)).thenReturn(loan);

        mockMvc.perform(put("/api/loans/{id}/return", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(loan.getDate().toString()));
    }
}