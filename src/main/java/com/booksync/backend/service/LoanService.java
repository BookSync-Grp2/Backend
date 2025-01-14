package com.booksync.backend.service;

import com.booksync.backend.model.Loan;
import com.booksync.backend.repository.LoanRepository;
import com.booksync.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
    }

    public List<Loan> getUserLoans(Long userId){
        if(!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return loanRepository.findByUserId(userId);
    }
}
