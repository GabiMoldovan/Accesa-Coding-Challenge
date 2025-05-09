package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

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
}
