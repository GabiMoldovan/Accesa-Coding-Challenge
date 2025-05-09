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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "the currency of the item")
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "")
    private Store store;

    @Transient
    public float getTotalPrice() {
        return pricePerUnit * units;
    }
}
