package com.booksync.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Filter for JWT-based authentication.
 * Processes JWT tokens from the Authorization header and authenticates users.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final SecretKey key;

    /**
     * Initializes the JWT filter with a secret key for token verification.
     * @param key Secret key used for JWT signature verification
     */
    public JwtAuthenticationFilter(SecretKey key) {
        this.key = key;
    }

    /**
     * Processes each request to authenticate users based on JWT tokens.
     * Extracts JWT from Authorization header, validates it, and sets up SecurityContext.
     *
     * @param request The HTTP request
     * @param response The HTTP response
     * @param filterChain The filter chain for additional processing
     * @throws ServletException if a servlet error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String email = claims.getSubject();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}
