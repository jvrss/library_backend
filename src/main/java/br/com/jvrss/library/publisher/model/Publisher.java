package br.com.jvrss.library.publisher.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String address;
}