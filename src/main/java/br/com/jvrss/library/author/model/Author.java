// src/main/java/br/com/jvrss/library/author/model/Author.java
package br.com.jvrss.library.author.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Size(max = 200)
    @Column(length = 200)
    private String name;

    @Size(max = 1000)
    @Column(length = 2000)
    private String bio;

    @Temporal(TemporalType.DATE)
    private LocalDate birthday;
}