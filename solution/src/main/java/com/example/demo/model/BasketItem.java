package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "basket_items")
@Setter
@Getter
@NoArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "An instance of an item in a basket")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "basket_id", nullable = false)
    @Schema(description = "the basket to which this item belongs")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @Schema(description = "The item that's in the basket")
    private StoreItem storeItem;

    @Column(nullable = false)
    @Schema(description = "The quantity of that item")
    private float quantity;

    @Schema(description = "Price per unit at the moment of adding to basket")
    private float priceAtAddition;

    public BasketItem(StoreItem storeItem, float quantity) {
        this.storeItem = storeItem;
        this.quantity = quantity;
        this.priceAtAddition = storeItem.getPricePerUnit();
    }
}
