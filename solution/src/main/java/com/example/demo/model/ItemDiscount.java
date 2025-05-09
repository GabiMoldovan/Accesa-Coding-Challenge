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
    @JoinColumn(name = "item_id", nullable = false)
    @Schema(description = "")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "")
    private Store store;

    @Column(nullable = false)
    @Schema(description = "")
    private float oldPrice;

    @Column( nullable = false)
    @Schema(description = "")
    private float discountProcentage;

    @Column(nullable = false)
    @Schema(description = "")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @Schema(description = "")
    private LocalDateTime endDate;
}
