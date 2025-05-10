package com.example.demo.model;

import com.example.demo.model.enums.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "store_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the item in the store")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    @Schema(description = "")
    private Item item;

    @Column(nullable = false)
    @Schema(description = "")
    private float pricePerUnit;

    @Column(nullable = false)
    @Schema(description = "")
    private float units;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "")
    private Currency currency;

    @Transient
    public float getTotalPrice() {
        return pricePerUnit * units;
    }
}
