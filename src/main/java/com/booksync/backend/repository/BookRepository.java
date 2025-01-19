package com.booksync.backend.repository;

import com.booksync.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByISBN(String ISBN);

    @Query("SELECT b FROM Book b WHERE " +
            "(LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(b.ISBN) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "AND b.isAvailable = :available")
    List<Book> searchBooks(@Param("query") String query, @Param("available") boolean available);
}
