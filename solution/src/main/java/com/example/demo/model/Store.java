package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "stores")
@Setter
@Getter
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the ")
    private Long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "")
    private String companyName;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "The items that the store has")
    private List<StoreItem> storeItems;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "")
    private List<ItemDiscount> discounts;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "")
    private List<PriceHistory> priceHistoryEntries;
}
