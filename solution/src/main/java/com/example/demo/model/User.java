package com.example.demo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "the id of the user")
    private Long id;

    @Column(nullable = false, length = 64)
    @Schema(description = "The user's first name")
    private String firstName;

    @Column(nullable = false, length = 64)
    @Schema(description = "The user's last name")
    private String lastName;

    @Column(nullable = false, length = 64)
    @Schema(description = "The user's email")
    private String email;

    @Column(nullable = false, length = 64)
    @Schema(description = "The user's password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "the list of spendings of the user")
    private List<Spending> spendings;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "the list of items that the user has in their basket")
    private List<BasketItem> basket;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "the list of items that the user has set an alert for")
    private List<PriceAlert> priceAlerts = new ArrayList<>();

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
