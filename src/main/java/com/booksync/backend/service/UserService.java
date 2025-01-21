package com.booksync.backend.service;

import com.booksync.backend.model.User;
import com.booksync.backend.dto.UserUpdateRequest;
import com.booksync.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for handling user-related business logic.
 */
@Service
public class UserService {
    private final UserRepository userRepository;

    /**
     * Initializes the user service with required repository.
     * @param userRepository Repository for user data access
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Updates a user's information with non-null fields from the update request.
     * Checks for email uniqueness if email is being updated.
     *
     * @param userId ID of the user to update
     * @param updateRequest DTO containing the fields to update
     * @return The updated user
     * @throws EntityNotFoundException if user not found
     * @throws IllegalArgumentException if new email is already taken
     */
    public User updateUser(Long userId, UserUpdateRequest updateRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (updateRequest.getFirstName() != null) {
            user.setFirstName(updateRequest.getFirstName());
        }
        if (updateRequest.getLastName() != null) {
            user.setLastName(updateRequest.getLastName());
        }
        if (updateRequest.getEmail() != null) {
            if (userRepository.existsByEmail(updateRequest.getEmail()) &&
                    !user.getEmail().equals(updateRequest.getEmail())) {
                throw new IllegalArgumentException("Email is already taken");
            }
            user.setEmail(updateRequest.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their email address.
     *
     * @param email Email address of the user to find
     * @return The found user
     * @throws EntityNotFoundException if no user found with given email
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    /**
     * Deletes a user from the system.
     *
     * @param userId ID of the user to delete
     * @throws EntityNotFoundException if user not found
     */
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
