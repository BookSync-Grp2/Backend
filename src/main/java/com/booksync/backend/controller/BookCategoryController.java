package com.booksync.backend.controller;

import com.booksync.backend.model.BookCategory;
import com.booksync.backend.repository.BookCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book-category")
public class BookCategoryController {

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @GetMapping("")
    public ResponseEntity<List<BookCategory>> getLoans() {
        return ResponseEntity.ok(bookCategoryRepository.findAll());
    }
}