package br.com.jvrss.library.book.model;

import br.com.jvrss.library.author.model.Author;
import br.com.jvrss.library.language.model.Language;
import br.com.jvrss.library.publisher.model.Publisher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Size(max = 200)
    private String name;

    @Size(max = 2000)
    private String description;

    @NotNull
    @Pattern(regexp = "\\d{13}")
    private String isbn;

    @Min(1)
    private int pages;

    @NotNull
    private LocalDate publication;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
}