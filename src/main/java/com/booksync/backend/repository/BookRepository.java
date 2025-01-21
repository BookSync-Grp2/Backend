package com.booksync.backend.repository;

import com.booksync.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Book entity operations.
 * Extends JPA repository for standard CRUD operations.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Searches for books based on a query string and availability status.
     * Performs a case-insensitive search across title, author, and ISBN fields.
     *
     * @param query Search term to match against book fields
     * @param available Filter flag for book availability
     * @return List of books matching the search criteria
     */
    @Query("SELECT b FROM Book b WHERE " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.ISBN) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND b.isAvailable = :available")
    List<Book> searchBooks(@Param("query") String query, @Param("available") boolean available);
}
