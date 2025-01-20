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

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final LoanService loanService;

    public LoanController(LoanRepository loanRepository, UserRepository userRepository, LoanService loanService) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.loanService = loanService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Loan>> getLoans() {
        return ResponseEntity.ok(loanRepository.findAll());
    }

    @GetMapping("/all/user")
    public ResponseEntity<List<LoanDTO>> getAllUserLoans(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<Loan> loans = loanService.getAllUserLoans(userId);
        List<LoanDTO> loanDTOs = loans.stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loanDTOs);
    }

    @GetMapping("/current")
    public ResponseEntity<List<LoanDTO>> getCurrentUserLoans(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<Loan> loans = loanService.getCurrentUserLoans(userId);
        List<LoanDTO> loanDTOs = loans.stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loanDTOs);
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String userEmail = authentication.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + userEmail));
        return user.getId();
    }

    @GetMapping("/previous")
    public ResponseEntity<List<LoanDTO>> getPreviousUserLoans(Authentication authentication) {
        Long userId = getUserIdFromAuthentication(authentication);
        List<Loan> loans = loanService.getPreviousUserLoans(userId);
        List<LoanDTO> loanDTOs = loans.stream()
                .map(LoanDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loanDTOs);
    }


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