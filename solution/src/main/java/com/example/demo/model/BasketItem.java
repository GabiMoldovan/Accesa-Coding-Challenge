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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "the user that owns the basket")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @Schema(description = "")
    private Item item;

    @Column(nullable = false)
    @Schema(description = "")
    private float quantity;

    @Schema(description = "")
    private float priceAtAddition;

    public BasketItem(User user, Item item, float quantity) {
        this.user = user;
        this.item = item;
        this.quantity = quantity;
        this.priceAtAddition = item.getPricePerUnit();
    }
}
