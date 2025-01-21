package com.booksync.backend.repository;

import com.booksync.backend.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for BookCategory entity operations.
 * Extends JPA repository for standard CRUD operations.
 */
@Repository
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {
}
