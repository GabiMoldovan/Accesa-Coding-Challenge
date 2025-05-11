package com.example.demo.model;

import com.example.demo.model.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_items")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StoreItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of an instance of an item that the store has for sale")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "the store id that the instance of the item can be found at")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @Schema(description = "the id of the item")
    private Item item;

    @Column(nullable = false)
    @Schema(description = "the price per unit of the item")
    private float pricePerUnit;

    @Column(nullable = false)
    @Schema(description = "the number of units that the package contains")
    private float units;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "the currency at which the item is for sale")
    private Currency currency;

    @Transient
    public float getTotalPrice() {
        return pricePerUnit * units;
    }
}
