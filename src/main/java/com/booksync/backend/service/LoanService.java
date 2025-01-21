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

/**
 * Service class for handling loan-related business logic.
 */
@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    /**
     * Service class for handling loan-related business logic.
     */
    public LoanService(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves current (not returned) loans for a specific user.
     *
     * @param userId ID of the user
     * @return List of current loans
     * @throws EntityNotFoundException if user not found
     */
    public List<Loan> getCurrentUserLoans(Long userId){
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return getAllUserLoans(userId).stream()
                .filter(loan -> !loan.isReturned())
                .toList();
    }

    /**
     * Retrieves previous (returned) loans for a specific user.
     *
     * @param userId ID of the user
     * @return List of previous loans
     * @throws EntityNotFoundException if user not found
     */
    public List<Loan> getPreviousUserLoans(Long userId){
        if(!userRepository.existsById(userId)){
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return getAllUserLoans(userId).stream()
                .filter(Loan::isReturned)
                .toList();
    }

    /**
     * Retrieves all loans (current and previous) for a specific user.
     *
     * @param userId ID of the user
     * @return List of all loans
     * @throws EntityNotFoundException if user not found
     */
    public List<Loan> getAllUserLoans(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return loanRepository.findByUserId(userId);
    }

    /**
     * Creates a new loan for a book to a user.
     * Sets loan duration to 60 days and marks the book as unavailable.
     *
     * @param userId ID of the user borrowing the book
     * @param bookId ID of the book being borrowed
     * @return The created loan
     * @throws EntityNotFoundException if user or book not found
     * @throws IllegalStateException if book is not available
     */
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

    /**
     * Marks a loan as returned and makes the associated book available again.
     *
     * @param id ID of the loan to return
     * @return The updated loan
     * @throws EntityNotFoundException if loan or associated book not found
     */
    public Loan returnLoan(Long id){
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + id));
        loan.setReturned(true);
        Book book = bookRepository.findById(loan.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + loan.getBookId()));
        book.setAvailable(true);
        return loanRepository.save(loan);
    }

    /**
     * Marks a loan as retrieved by the user.
     *
     * @param id ID of the loan to mark as retrieved
     * @return The updated loan
     * @throws EntityNotFoundException if loan not found
     */
    public Loan retrieveLoan(Long id){
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found with id: " + id));
        loan.setRetrieved(true);
        return loanRepository.save(loan);
    }
}
