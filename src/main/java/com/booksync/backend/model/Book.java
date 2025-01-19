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
    private String coverUrl;

    @Column(nullable = false)
    private int publishedYear;

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
    public String getCoverUrl() {
        return coverUrl;
    }
    public int getPublishedYear() {
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
    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }
    public void setPublishedYear(int publishedYear) {
        this.publishedYear = publishedYear;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", publishedYear=" + publishedYear +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
