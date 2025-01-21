package com.booksync.backend.repository;

import com.booksync.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Category entity operations.
 * Extends JPA repository for standard CRUD operations.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
