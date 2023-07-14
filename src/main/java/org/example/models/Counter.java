package org.example.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Counter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "integer default 0")
    private int counterValue;
}

