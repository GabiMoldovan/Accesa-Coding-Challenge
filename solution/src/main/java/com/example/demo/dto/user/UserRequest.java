package com.example.demo.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(

        @Schema(description = "The user's first name")
        @NotNull(message = "First name cannot be null")
        @Size(min = 1, max = 64, message = "First name must be between 1 and 64 characters")
        String firstName,

        @Schema(description = "The user's last name")
        @NotNull(message = "Last name cannot be null")
        @Size(min = 1, max = 64, message = "Last name must be between 1 and 64 characters")
        String lastName,

        @Schema(description = "The user's email address")
        @NotNull(message = "Email cannot be null")
        @Email(message = "Email should be valid")
        String email,

        @Schema(description = "The user's password")
        @NotNull(message = "Password cannot be null")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
) {
    public UserRequest(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
