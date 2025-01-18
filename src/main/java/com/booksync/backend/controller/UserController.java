package com.booksync.backend.controller;

import com.booksync.backend.dto.UserDTO;
import com.booksync.backend.dto.UserUpdateRequest;
import com.booksync.backend.model.Loan;
import com.booksync.backend.model.User;
import com.booksync.backend.repository.UserRepository;
import com.booksync.backend.service.LoanService;
import com.booksync.backend.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for managing User-related operations.
 * Provides endpoints for CRUD operations on users and retrieving user loans.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserRepository userRepository;
    private final LoanService loanService;
    private final UserService userService;

    /**
     * Constructs a new UserController with required dependencies.
     *
     * @param userRepository Repository for user data access
     * @param loanService Service for managing loan operations
     * @param userService Service for managing user operations
     */
    public UserController(UserRepository userRepository, LoanService loanService, UserService userService) {
        this.userRepository = userRepository;
        this.loanService = loanService;
        this.userService = userService;
    }

    /**
     * Retrieves all users in the system.
     *
     * @return ResponseEntity containing a list of all users
     */
    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id The ID of the user to retrieve
     * @return ResponseEntity containing the user if found, or a not found response
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates a user's information.
     *
     * @param id The ID of the user to update
     * @param userUpdateRequest DTO containing the fields to update
     * @return ResponseEntity containing the updated user if successful, or a not found response
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest) {
        try {
            User updatedUser = userService.updateUser(id, userUpdateRequest);
            return ResponseEntity.ok(updatedUser);
        }  catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all loans associated with a specific user.
     *
     * @param id The ID of the user whose loans to retrieve
     * @return ResponseEntity containing a list of the user's loans if found, or a not found response
     */
    @GetMapping("/{id}/loans")
    public ResponseEntity<List<Loan>> getUserLoans(@PathVariable Long id) {
        try {
            List<Loan> loans = loanService.getUserLoans(id);
            return ResponseEntity.ok(loans);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new user in the system.
     *
     * @param user The user entity to create
     * @return The created user entity with assigned ID
     */
    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }


    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();

        User user = userService.getUserByEmail(userEmail);
        return ResponseEntity.ok(UserDTO.fromUser(user));
    }
}