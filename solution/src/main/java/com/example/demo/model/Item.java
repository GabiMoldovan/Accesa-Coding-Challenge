package com.example.demo.model;

import com.example.demo.model.enums.Category;
import com.example.demo.model.enums.Currency;
import com.example.demo.model.enums.UnitType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the item")
    private Long id;

    @Column(nullable = false, length = 128)
    @Schema(description = "the name of an item")
    private String itemName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "the category of an item")
    private Category category;

    @Column(nullable = false, length = 64)
    @Schema(description = "the brand of an item")
    private String brand;

    @Column(nullable = false)
    @Schema(description = "the number of units of the item")
    private float units;

    @Column(nullable = false)
    @Schema(description = "the price per unit of the item")
    private float pricePerUnit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "the unit type of the item")
    private UnitType unitType;

    @Column(nullable = false)
    @Schema(description = "the ")
    float totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "the currency of the item")
    private Currency currency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spendings_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    @Schema(description = "the spending entry to which the item belongs")
    private Spending spending;

    public Item(String itemName, Category category, String brand, float units, float pricePerUnit, UnitType unitType, float totalPrice, Currency currency, Spending spending) {
        this.itemName = itemName;
        this.category = category;
        this.brand = brand;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
        this.unitType = unitType;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.spending = spending;
    }
}
