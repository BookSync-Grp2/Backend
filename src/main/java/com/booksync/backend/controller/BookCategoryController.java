package com.booksync.backend.controller;

import com.booksync.backend.model.BookCategory;
import com.booksync.backend.repository.BookCategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing book category operations.
 */
@RestController
@RequestMapping("/api/book-category")
public class BookCategoryController {

    private final BookCategoryRepository bookCategoryRepository;

    /**
     * Initializes the book category controller with required repository.
     * @param bookCategoryRepository Repository for book category data access
     */
    public BookCategoryController(BookCategoryRepository bookCategoryRepository) {
        this.bookCategoryRepository = bookCategoryRepository;
    }

    /**
     * Retrieves all book categories from the database.
     * @return ResponseEntity containing a list of all book categories
     */
    @GetMapping("")
    public ResponseEntity<List<BookCategory>> getLoans() {
        return ResponseEntity.ok(bookCategoryRepository.findAll());
    }
}