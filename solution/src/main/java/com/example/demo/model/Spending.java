package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "spendings")
@Setter
@Getter
@NoArgsConstructor
public class Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the ")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "")
    private Store store;

    @OneToMany(mappedBy = "spending", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "")
    private List<PurchasedItem> purchasedItems;

    @Column(nullable = false)
    @Schema(description = "")
    private LocalDateTime purchaseDate;

    @Column(nullable = false)
    @Schema(description = "")
    private float totalPrice;
}
