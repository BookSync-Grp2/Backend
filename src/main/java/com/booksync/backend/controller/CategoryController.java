package com.booksync.backend.controller;

import com.booksync.backend.model.Category;
import com.booksync.backend.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing categories.
 */
@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    /**
     * Initializes the category controller with required repository.
     * @param categoryRepository Repository for category data access
     */
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Retrieves all categories from the database.
     * @return ResponseEntity containing a list of all categories
     */
    @GetMapping("")
    public ResponseEntity<List<Category>> getLoans() {
        return ResponseEntity.ok(categoryRepository.findAll());
    }
}
