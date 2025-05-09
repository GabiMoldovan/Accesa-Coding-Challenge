package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "purchased_items")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedItem {
    private Long id;
}
