package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "stores")
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the store")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "the name of the company")
    private String companyName;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "The items that the store has for sale")
    private List<StoreItem> storeItems;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "the list of discounts that are applied to the items")
    private List<ItemDiscount> discounts;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "the price history of the items")
    private List<PriceHistory> priceHistoryEntries;
}
