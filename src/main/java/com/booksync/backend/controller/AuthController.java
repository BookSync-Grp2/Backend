package com.booksync.backend.controller;

import com.booksync.backend.dto.UserDTO;
import com.booksync.backend.model.LoginRequest;
import com.booksync.backend.model.User;
import com.booksync.backend.repository.UserRepository;
import com.booksync.backend.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody User user) {
        authService.register(user);
        String token = authService.authenticate(user.getEmail(), user.getPassword());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", UserDTO.fromUser(user));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isEmpty()) return ResponseEntity.notFound().build();

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("user", UserDTO.fromUser(user.get()));

        return ResponseEntity.ok(response);
    }
}
