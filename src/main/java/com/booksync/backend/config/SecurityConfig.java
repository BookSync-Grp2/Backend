package com.booksync.backend.config;

import com.booksync.backend.security.JwtAuthenticationFilter;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Configuration class for security settings, including JWT authentication, password encoding,
 * and CORS configuration.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final SecretKey key;

    /**
     * Initializes the security configuration with a JWT secret key.
     * @param SECRET_KEY The secret key used for JWT token signing
     */
    public SecurityConfig(@Value("${jwt.secret}") String SECRET_KEY) {
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Configures the security filter chain for HTTP requests.
     * Sets up CORS, disables CSRF for REST API, configures stateless session management,
     * adds JWT authentication, and defines endpoint access rules.
     *
     * @param http The HttpSecurity object to configure
     * @return The built SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource((corsConfigurationSource())))
                .csrf(AbstractHttpConfigurer::disable) // Désactivation CSRF pour une API REST
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless: Utilisation de JWT
                .addFilterBefore(new JwtAuthenticationFilter(key), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/user/me").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)  // Désactivation HTTP Basic
                .formLogin(AbstractHttpConfigurer::disable) ; // Pas de formulaire login natif
        return http.build();
    }

    /**
     * Provides the password encoder bean for secure password hashing.
     * @return BCryptPasswordEncoder instance for password encryption
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Pour chiffrer les mots de passe
    }

    /**
     * Creates the authentication manager bean for handling authentication requests.
     * @param authConfig The authentication configuration
     * @return The configured AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configures CORS settings for the application.
     * Allows all origins (*), common HTTP methods, and necessary headers including Authorization.
     *
     * @return CorsConfigurationSource with the defined CORS settings
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}