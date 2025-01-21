package com.booksync.backend.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the application.
 * Provides centralized exception handling across all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EntityNotFoundException across the application.
     * Converts the exception into a 404 NOT_FOUND response.
     *
     * @param ex The caught EntityNotFoundException
     * @return ResponseEntity with NOT_FOUND status and error message
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles IllegalStateException across the application.
     * Converts the exception into a 400 BAD_REQUEST response.
     *
     * @param ex The caught IllegalStateException
     * @return ResponseEntity with BAD_REQUEST status and error message
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}