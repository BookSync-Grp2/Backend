package com.booksync.backend.repository;

import com.booksync.backend.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userId);

    @Query("SELECT l " +
            "FROM Loan l " +
            "WHERE l.userId = :userId " +
            "AND l.isReturned = false " +
            "AND l.loanEndDate >= :currentDate " +
            "ORDER BY l.loanEndDate ASC")
    List<Loan> findCurrentLoansByUserId(@Param("userId") Long userId, @Param("currentDate") Date currentDate);
}
