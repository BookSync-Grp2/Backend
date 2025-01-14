package com.booksync.backend.service;

import com.booksync.backend.model.User;
import com.booksync.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String SECRET_KEY = "3vfuaiCNZF54s98q6ppnzMp4KTPdPyFTkYeRUejdu4QS32FSCXW4J8Q";

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDateJoined(new Date());
        userRepository.save(user);
    }

    public String authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return generateToken(user.get());
        }
        throw new RuntimeException("Invalid email or password");
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRoleType())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
