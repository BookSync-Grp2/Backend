package com.booksync.backend.service;

import com.booksync.backend.model.Book;
import com.booksync.backend.model.Loan;
import com.booksync.backend.repository.BookRepository;
import com.booksync.backend.repository.LoanRepository;
import com.booksync.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public List<Loan> getCurrentUserLoans(Long userId){
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return getAllUserLoans(userId).stream()
                .filter(loan -> !loan.isReturned())
                .toList();
    }

    public List<Loan> getPreviousUserLoans(Long userId){
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return getAllUserLoans(userId).stream()
                .filter(Loan::isReturned)
                .toList();
    }

    public List<Loan> getAllUserLoans(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return loanRepository.findByUserId(userId);
    }

    public Loan createLoan(Long userId, Long bookId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        var book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for loan");
        }

        Loan loan = new Loan();
        loan.setUserId(userId);
        loan.setBookId(bookId);
        loan.setLoanStartDate(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 60);
        loan.setLoanEndDate(calendar.getTime());

        loan.setRetrieved(false);
        loan.setReturned(false);

        book.setAvailable(false);
        bookRepository.save(book);

        return loanRepository.save(loan);
    }

    public Loan returnLoan(Long id){
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + id));
        loan.setReturned(true);
        Book book = bookRepository.findById(loan.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + loan.getBookId()));
        book.setAvailable(true);
        return loanRepository.save(loan);
    }

    public Loan retrieveLoan(Long id){
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + id));
        loan.setRetrieved(true);
        return loanRepository.save(loan);
    }
}
