package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(name = "store_item_id", nullable = false)
    @Schema(description = "")
    private StoreItem storeItem;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "")
    private Store store;

    @Column(name = "date", nullable = false)
    @Schema(description = "")
    private LocalDateTime date;

    @Column(name = "price", nullable = false)
    @Schema(description = "")
    private float price;
}
