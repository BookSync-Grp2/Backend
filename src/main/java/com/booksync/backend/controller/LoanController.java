package com.booksync.backend.controller;

import com.booksync.backend.model.Loan;
import com.booksync.backend.repository.LoanRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanRepository loanRepository;

    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Loan>> getLoans() {
        return ResponseEntity.ok(loanRepository.findAll());
    }
}
