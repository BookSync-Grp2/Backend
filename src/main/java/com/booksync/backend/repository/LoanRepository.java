package com.booksync.backend.repository;

import com.booksync.backend.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Loan entity operations.
 * Extends JPA repository for standard CRUD operations.
 */
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    /**
     * Finds all loans associated with a specific user.
     *
     * @param userId The ID of the user whose loans to retrieve
     * @return List of loans belonging to the specified user
     */
    List<Loan> findByUserId(Long userId);
}
