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
@ToString
@NoArgsConstructor
public class Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the spending")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Schema(description = "the user id that made the spending")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @Schema(description = "the store id that the user made the spending at")
    private Store store;

    @OneToMany(mappedBy = "spending", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "the list of purchased items")
    private List<PurchasedItem> purchasedItems;

    @Column(nullable = false)
    @Schema(description = "the total price of the purchase")
    private float totalPrice;

    @Column(nullable = false)
    @Schema(description = "the date that the user made the purchase")
    private LocalDateTime purchaseDate;
}
