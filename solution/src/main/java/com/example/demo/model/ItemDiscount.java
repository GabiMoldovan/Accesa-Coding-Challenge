package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_discounts")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the item discount")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "store_item_id", nullable = false)
    @Schema(description = "the id of the item in the store")
    private StoreItem storeItem;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "the id of the store where the discount is going on")
    private Store store;

    @Column(nullable = false)
    @Schema(description = "the old price of the item")
    private float oldPrice;

    @Column(nullable = false)
    @Schema(description = "the % of the discount")
    private float discountPercentage;

    @Column(nullable = false)
    @Schema(description = "the date when the discount starts")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @Schema(description = "the date when the discount ends")
    private LocalDateTime endDate;
}
