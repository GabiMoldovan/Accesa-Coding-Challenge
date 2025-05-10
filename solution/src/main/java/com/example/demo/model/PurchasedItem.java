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
    @Schema(description = "")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "spending_id", nullable = false)
    @Schema(description = "")
    private Spending spending;

    @Column(nullable = false)
    @Schema(description = "")
    private String itemName;

    @Column(nullable = false)
    @Schema(description = "")
    private Float pricePerUnit;

    @Column(nullable = false)
    @Schema(description = "")
    private Float units;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitType unitType;
}
