package com.booksync.backend.service;

import com.booksync.backend.model.User;
import com.booksync.backend.repository.UserRepository;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

/**
 * Service handling user authentication and registration operations.
 * This service manages user registration, authentication, and JWT token generation.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecretKey key;

    /**
     * Constructs an AuthService with the specified secret key.
     * Initializes the JWT signing key from the provided secret.
     *
     * @param SECRET_KEY The secret key loaded from application properties used for JWT signing
     */
    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${jwt.secret}") String SECRET_KEY
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Registers a new user in the system.
     * The password is encrypted before saving, and the registration date is set to current time.
     *
     * @param user The user entity to be registered
     */
    public void register(User user) {
        user.setDateJoined(new Date());

        User userToSave = new User();
        userToSave.setFirstName(user.getFirstName());
        userToSave.setLastName(user.getLastName());
        userToSave.setEmail(user.getEmail());
        userToSave.setPassword(passwordEncoder.encode(user.getPassword()));
        userToSave.setDateJoined(new Date());
        userToSave.setValidated(false);
        userToSave.setRoleType(user.getRoleType());

        userRepository.save(userToSave);
    }

    /**
     * Authenticates a user based on email and password.
     * If authentication is successful, generates and returns a JWT token.
     *
     * @param email The user's email address
     * @param password The user's password
     * @return JWT token if authentication is successful
     * @throws RuntimeException if the email/password combination is invalid
     */
    public String authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

/*        System.out.println(password);
        System.out.println(user.get().getPassword());
        System.out.println(passwordEncoder.matches(password, user.get().getPassword()));*/

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return generateToken(user.get());
        }
        throw new RuntimeException("Invalid email or password");
    }


    /**
     * Generates a JWT token for a specified user.
     * The token includes the following claims:
     * - Subject (email)
     * - User ID
     * - First Name
     * - Last Name
     * - Role
     * - Date Joined
     * The token expires after 10 hours.
     *
     * @param user The user for whom to generate the token
     * @return JWT token containing user information
     */
    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
                .signWith(key)
                .compact();
    }
}


