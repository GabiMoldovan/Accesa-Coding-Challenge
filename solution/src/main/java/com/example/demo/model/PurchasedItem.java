package com.example.demo.model;

import com.example.demo.model.enums.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchased_items")
@Setter
@Getter
@NoArgsConstructor
public class PurchasedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the purchase")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "spending_id", nullable = false)
    @Schema(description = "the spending id to which the item belongs to")
    private Spending spending;

    @Column(nullable = false)
    @Schema(description = "the item name")
    private String itemName;

    @Column(nullable = false)
    @Schema(description = "price per unit at the moment of purchase")
    private float pricePerUnit;

    @Column(nullable = false)
    @Schema(description = "number of units at the moment of purchase")
    private float units;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "the unit type of the item at the moment of purchase")
    private UnitType unitType;
}
