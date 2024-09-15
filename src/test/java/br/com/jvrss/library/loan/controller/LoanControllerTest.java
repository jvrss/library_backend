package br.com.jvrss.library.loan.controller;

import br.com.jvrss.library.loan.model.Loan;
import br.com.jvrss.library.loan.service.LoanService;
import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.user.model.User;
import br.com.jvrss.library.util.CPFGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
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
    private ObjectMapper objectMapper;

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loanController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void testCreateLoan() throws Exception {
        Loan loan = createLoan();

        when(loanService.createLoan(any(Loan.class))).thenReturn(loan);

        mockMvc.perform(post("/api/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loan)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.date").value(loan.getDate().toString()))
                .andExpect(jsonPath("$.returned").value(loan.getReturned().toString()))
                .andExpect(jsonPath("$.user.id").value(loan.getUser().getCpf()))
                .andExpect(jsonPath("$.employee.id").value(loan.getEmployee().getCpf()))
                .andExpect(jsonPath("$.book.id").value(loan.getBook().getId().toString()));
    }

    @Test
    void testGetLoanById() throws Exception {
        UUID id = UUID.randomUUID();
        Loan loan = createLoan();
        loan.setId(id);

        when(loanService.getLoanById(id)).thenReturn(loan);

        mockMvc.perform(get("/api/loans/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(loan.getDate().toString()))
                .andExpect(jsonPath("$.returned").value(loan.getReturned().toString()))
                .andExpect(jsonPath("$.user.id").value(loan.getUser().getCpf()))
                .andExpect(jsonPath("$.employee.id").value(loan.getEmployee().getCpf()))
                .andExpect(jsonPath("$.book.id").value(loan.getBook().getId().toString()));
    }

    @Test
    void testGetAllLoans() throws Exception {
        Loan loan = createLoan();
        List<Loan> loans = Collections.singletonList(loan);

        when(loanService.getAllLoans()).thenReturn(loans);

        mockMvc.perform(get("/api/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date").value(loan.getDate().toString()))
                .andExpect(jsonPath("$[0].returned").value(loan.getReturned().toString()))
                .andExpect(jsonPath("$[0].user.id").value(loan.getUser().getCpf()))
                .andExpect(jsonPath("$[0].employee.id").value(loan.getEmployee().getCpf()))
                .andExpect(jsonPath("$[0].book.id").value(loan.getBook().getId().toString()));
    }

    @Test
    void testUpdateLoan() throws Exception {
        UUID id = UUID.randomUUID();
        Loan loan = createLoan();
        loan.setId(id);

        when(loanService.updateLoan(any(UUID.class), any(Loan.class))).thenReturn(loan);

        mockMvc.perform(put("/api/loans/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(loan)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(loan.getDate().toString()))
                .andExpect(jsonPath("$.returned").value(loan.getReturned().toString()))
                .andExpect(jsonPath("$.user.id").value(loan.getUser().getCpf()))
                .andExpect(jsonPath("$.employee.id").value(loan.getEmployee().getCpf()))
                .andExpect(jsonPath("$.book.id").value(loan.getBook().getId().toString()));
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
        Loan loan = createLoan();
        loan.setId(id);

        when(loanService.updateReturnedTime(id)).thenReturn(loan);

        mockMvc.perform(put("/api/loans/{id}/return", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(loan.getDate().toString()))
                .andExpect(jsonPath("$.returned").value(loan.getReturned().toString()))
                .andExpect(jsonPath("$.user.id").value(loan.getUser().getCpf()))
                .andExpect(jsonPath("$.employee.id").value(loan.getEmployee().getCpf()))
                .andExpect(jsonPath("$.book.id").value(loan.getBook().getId().toString()));
    }

    private Loan createLoan() {
        Loan loan = new Loan();
        loan.setId(UUID.randomUUID());
        loan.setDate(LocalDateTime.now());
        loan.setReturned(LocalDateTime.now().plusDays(14));

        User user = new User();
        user.setCpf(CPFGenerator.generateCPF());
        loan.setUser(user);

        Employee employee = new Employee();
        employee.setCpf(CPFGenerator.generateCPF());
        loan.setEmployee(employee);

        Book book = new Book();
        book.setId(UUID.randomUUID());
        loan.setBook(book);

        return loan;
    }

    private String asJsonString(final Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}