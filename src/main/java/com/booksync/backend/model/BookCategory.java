package com.booksync.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name  = "BookCategory")
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long bookId;

    @Column(nullable = false)
    private long categoryId;

    public long getId() {
        return id;
    }
    public long getBookId() {
        return bookId;
    }
    public long getCategoryId() {
        return categoryId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }
    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
