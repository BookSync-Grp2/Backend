package com.booksync.backend.controller;

import com.booksync.backend.model.User;
import com.booksync.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // Creation of a user for testing purposes
    @GetMapping("/createUser")
    public ResponseEntity<String> createCustomUser(){
        User customUser = new User();

        customUser.setFirstName("John");
        customUser.setLastName("Doe");
        customUser.setEmail("johnDoe@email.com");
        customUser.setDateJoined(new Date());
        customUser.setValidated(true);
        userRepository.save(customUser);

        return ResponseEntity.ok("User John Doe created successfully");
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}