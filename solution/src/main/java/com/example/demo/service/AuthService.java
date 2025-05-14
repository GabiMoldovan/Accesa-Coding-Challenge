package com.example.demo.service;

import com.example.demo.dto.user.UserRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.exceptions.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs an AuthService with the provided UserService and PasswordEncoder.
     *
     * @param userService the user service to manage user data
     * @param passwordEncoder the password encoder to encode user passwords
     */
    public AuthService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }



    /**
     * Registers a new user based on the provided user request.
     *
     * Checks if the email already exists in the system, and throws an AuthException
     * if the email is already in use. Otherwise, proceeds to save the user using UserService.
     *
     * @param userRequest the request object containing user registration details
     * @return a UserResponse object containing the saved user's information
     * @throws AuthException if a user with the same email already exists
     */
    public UserResponse signup(UserRequest userRequest) throws AuthException {
        if(userService.checkIfEmailExists(userRequest.email())) {
            throw new AuthException("Email already exists");
        }

        UserResponse userResponse = userService.saveUser(userRequest);

        return userResponse;
    }
}
