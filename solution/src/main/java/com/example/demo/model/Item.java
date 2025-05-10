package com.example.demo.model;

import com.example.demo.model.enums.Category;
import com.example.demo.model.enums.UnitType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "the unit type of the item")
    private UnitType unitType;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<StoreItem> storeItems;
}
