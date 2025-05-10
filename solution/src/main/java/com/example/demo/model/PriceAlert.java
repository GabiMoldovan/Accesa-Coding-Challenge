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
    @Schema(description = "the id of the user that sets the price alert")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_item_id", nullable = false)
    @Schema(description = "the id of the item in that store")
    private StoreItem storeItem;

    @Column(name = "target_price", nullable = false)
    @Schema(description = "the price that the user wishes to purchase the item at")
    private float targetPrice;
}
