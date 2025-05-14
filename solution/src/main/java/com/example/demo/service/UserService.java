package com.example.demo.service;

import com.example.demo.dto.userUpdate.PasswordChangeRequest;
import com.example.demo.dto.userUpdate.UserProfileRequest;
import com.example.demo.dto.user.UserRequest;
import com.example.demo.dto.user.UserResponse;
import com.example.demo.exceptions.AlreadyExistsException;
import com.example.demo.exceptions.AuthException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.utils.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * Saves a new user with the provided user request details
     *
     * @param userRequest the user request containing first name, last name, email, and password
     * @return the saved user as a response DTO
     */
    @Transactional
    public UserResponse saveUser(UserRequest userRequest) {
        String encodedPassword = passwordEncoder.encode(userRequest.password());

        User userToSave = new User(
                userRequest.firstName(),
                userRequest.lastName(),
                userRequest.email(),
                encodedPassword
        );
        return UserMapper.entityToDto(userRepository.save(userToSave));
    }


    /**
     * Deletes a user by their ID
     *
     * @param userId the ID of the user to delete
     */
    @Transactional
    public void deleteUser(Long userId) {
        User user = findById(userId);
        userRepository.delete(user);
    }



    /**
     * Updates an existing user's profile with the provided details
     *
     * @param userId the ID of the user to update
     * @param profileRequest the profile request containing updated first name, last name, and email
     * @return the updated user as a response DTO
     * @throws AlreadyExistsException if the email already exists in the system
     */
    @Transactional
    public UserResponse updateUser(Long userId, UserProfileRequest profileRequest) {
        User user = findById(userId);
        user.setFirstName(profileRequest.firstName());
        user.setLastName(profileRequest.lastName());

        if(!user.getEmail().equals(profileRequest.email())) {
            if(checkIfEmailExists(profileRequest.email())){
                throw new AlreadyExistsException("Email already exists" + profileRequest.email());
            }
            user.setEmail(profileRequest.email());
        }

        return UserMapper.entityToDto(userRepository.save(user));
    }


    /**
     * Changes the password of a user by verifying the old password and updating with the new password
     *
     * @param email the email of the user whose password is to be changed
     * @param passwordChangeRequest the request containing the old and new passwords
     * @throws AuthException.InvalidCredentialsException if the old password does not match
     */
    @Transactional
    @PreAuthorize("#email == authentication.principal.username")
    public void changePassword(String email, PasswordChangeRequest passwordChangeRequest) {
        User user = findByEmail(email);

        boolean matches = passwordEncoder.matches(passwordChangeRequest.oldPassword(), user.getPassword());

        if(!matches) {
            throw new AuthException.InvalidCredentialsException("Password does not match");
        }

        user.setPassword(passwordEncoder.encode(passwordChangeRequest.newPassword()));
        userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new AuthException.NotFoundException("Could not find user with id: " + userId));
    }

    public boolean checkIfEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new AuthException.NotFoundException("Could not find user with email: " + email));
    }

    public UserResponse findResponseByEmail(String email) {
        return UserMapper.entityToDto(findByEmail(email));
    }

    public UserResponse findResponseById(Long userId) {
        return UserMapper.entityToDto(findById(userId));
    }

    public List<User> getAllUsers() {return userRepository.findAll();}

    public List<UserResponse> getAllUserResponses() {
        return UserMapper.entityListToDto(getAllUsers());
    }

}
