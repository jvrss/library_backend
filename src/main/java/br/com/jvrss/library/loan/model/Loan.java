// src/main/java/br/com/jvrss/library/loan/model/Loan.java
package br.com.jvrss.library.loan.model;

import br.com.jvrss.library.book.model.Book;
import br.com.jvrss.library.employee.model.Employee;
import br.com.jvrss.library.user.model.User;
import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entity representing a loan.
 */
@Data
@Entity
public class Loan {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "returned")
    private LocalDateTime returned;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}