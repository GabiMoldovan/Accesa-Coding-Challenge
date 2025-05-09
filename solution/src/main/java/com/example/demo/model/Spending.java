package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "spendings")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the ")
    private Long id;
}
