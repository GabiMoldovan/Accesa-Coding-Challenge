package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "basket_items")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "An instance of an item in a basket")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "the user that owns the basket")
    private User user;

    @Column(nullable = false)
    @Schema(description = "The id of the item in the basket")
    private int itemId;

    @Column(nullable = false)
    @Schema(description = "the id of the store that the user is making the purchases")
    private Long storeId;

    @Column(nullable = false)
    @Schema(description = "the price per unit of the product at the moment of purchase")
    private float pricePerUnit;

    @Column(nullable = false)
    @Schema(description = "the number of units that the user wants to purchase")
    private float units;

    public BasketItem(int itemId, Long storeId, float pricePerUnit, float units) {
        this.itemId = itemId;
        this.storeId = storeId;
        this.pricePerUnit = pricePerUnit;
        this.units = units;
    }
}
