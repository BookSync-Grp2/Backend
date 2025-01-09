package com.booksync.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name  = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String ISBN;

    @Column(nullable = false)
    private Date publishedYear;

    @Column(nullable = false)
    private boolean isAvailable;

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getISBN() {
        return ISBN;
    }
    public Date getPublishedYear() {
        return publishedYear;
    }
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public void setPublishedYear(Date publishedYear) {
        this.publishedYear = publishedYear;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
