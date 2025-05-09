package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "price_history")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PriceHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the price history")
    private Long id;
}
