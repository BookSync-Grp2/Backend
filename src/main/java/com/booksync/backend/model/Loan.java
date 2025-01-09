package com.booksync.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private long bookId;

    @Column(nullable = false)
    private Date loanStartDate;

    @Column(nullable = false)
    private Date loanEndDate;

    @Column(nullable = false)
    private boolean isRetrieved;

    @Column(nullable = false)
    private boolean isReturned;

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

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
    public void setLoanStartDate(Date loanStartDate) {
        this.loanStartDate = loanStartDate;
    }
    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }
    public void setRetrieved(boolean retrieved) {
        isRetrieved = retrieved;
    }
    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}
