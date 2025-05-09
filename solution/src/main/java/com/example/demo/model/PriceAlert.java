package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "price_alerts")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the price alert")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @Schema(description = "")
    private Item item;

    @Column(name = "target_price", nullable = false)
    @Schema(description = "")
    private float targetPrice;
}
