package com.booksync.backend.dto;

import com.booksync.backend.model.Loan;
import java.util.Date;

public class LoanDTO {
    private long id;
    private long userId;
    private long bookId;
    private Date loanStartDate;
    private Date loanEndDate;
    private boolean isRetrieved;
    private boolean isReturned;

    // Constructor from Loan entity
    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.userId = loan.getUserId();
        this.bookId = loan.getBookId();
        this.loanStartDate = loan.getLoanStartDate();
        this.loanEndDate = loan.getLoanEndDate();
        this.isRetrieved = loan.isRetrieved();
        this.isReturned = loan.isReturned();
    }

    // Getters
    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getBookId() {
        return bookId;
    }

    public Date getLoanStartDate() {
        return loanStartDate;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public boolean isRetrieved() {
        return isRetrieved;
    }

    public boolean isReturned() {
        return isReturned;
    }
}