package com.booksync.backend.controller;

import com.booksync.backend.dto.CreateLoanRequest;
import com.booksync.backend.dto.LoanDTO;
import com.booksync.backend.model.Loan;
import com.booksync.backend.model.User;
import com.booksync.backend.repository.LoanRepository;
import com.booksync.backend.repository.UserRepository;
import com.booksync.backend.service.LoanService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing book loan operations.
 */
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final LoanService loanService;

    /**
     * Initializes the loan controller with required repositories and service.
     * @param loanRepository Repository for loan data access
     * @param userRepository Repository for user data access
     * @param loanService Service handling loan business logic
     */
    public LoanController(LoanRepository loanRepository, UserRepository userRepository, LoanService loanService) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.loanService = loanService;
    }

    /**
     * Retrieves all loans in the system.
     * @return ResponseEntity containing list of all loans
     */
    @GetMapping("/all")
    public ResponseEntity<List<Loan>> getLoans() {
        return ResponseEntity.ok(loanRepository.findAll());
    }

    /**
     * Retrieves all loans for the authenticated user.
     * @param authentication Current user's authentication details
     * @return ResponseEntity containing list of user's loans as DTOs
     */
    @GetMapping("/all/user")
    public ResponseEntity<List<LoanDTO>> getAllUserLoans(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<Loan> loans = loanService.getAllUserLoans(userId);
        List<LoanDTO> loanDTOs = loans.stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loanDTOs);
    }

    /**
     * Retrieves current (active) loans for the authenticated user.
     * @param authentication Current user's authentication details
     * @return ResponseEntity containing list of user's current loans as DTOs
     */
    @GetMapping("/current")
    public ResponseEntity<List<LoanDTO>> getCurrentUserLoans(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<Loan> loans = loanService.getCurrentUserLoans(userId);
        List<LoanDTO> loanDTOs = loans.stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loanDTOs);
    }

    /**
     * Extracts user ID from authentication details.
     * @param authentication Current user's authentication details
     * @return User ID
     * @throws EntityNotFoundException if user not found
     */
    private Long getUserIdFromAuthentication(Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));
        return user.getId();
    }

    /**
     * Retrieves previous (completed) loans for the authenticated user.
     * @param authentication Current user's authentication details
     * @return ResponseEntity containing list of user's previous loans as DTOs
     */
    @GetMapping("/previous")
    public ResponseEntity<List<LoanDTO>> getPreviousUserLoans(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<Loan> loans = loanService.getPreviousUserLoans(userId);
        List<LoanDTO> loanDTOs = loans.stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loanDTOs);
    }

    /**
     * Creates a new loan for a book.
     * Verifies that the authenticated user matches the request.
     *
     * @param request Contains user ID and book ID for the loan
     * @param authentication Current user's authentication details
     * @return ResponseEntity containing created loan as DTO, or appropriate error response
     */
    @PostMapping("/create")
    public ResponseEntity<LoanDTO> createLoan(
            @RequestBody CreateLoanRequest request,
            Authentication authentication) {
        // Verify the authenticated user matches the request
        Long authenticatedUserId = getUserIdFromAuthentication(authentication);
        if (authenticatedUserId != request.getUserId()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            Loan loan = loanService.createLoan(request.getUserId(), request.getBookId());
            return ResponseEntity.ok(new LoanDTO(loan));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Marks a loan as returned.
     * @param id Loan identifier
     * @return ResponseEntity containing updated loan as DTO, or appropriate error response
     */
    @PutMapping("/{id}/return")
    public ResponseEntity<LoanDTO> returnLoan(@PathVariable Long id) {
        try {
            Loan loan = loanService.returnLoan(id);
            return ResponseEntity.ok(new LoanDTO(loan));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Marks a loan as retrieved by the user.
     * @param id Loan identifier
     * @return ResponseEntity containing updated loan as DTO, or appropriate error response
     */
    @PutMapping("/{id}/retrieve")
    public ResponseEntity<LoanDTO> retrieveLoan(@PathVariable Long id) {
        try {
            Loan loan = loanService.retrieveLoan(id);
            return ResponseEntity.ok(new LoanDTO(loan));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}