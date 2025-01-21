package com.booksync.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Configuration class for SQLite database setup.
 */
@Configuration
public class SQLiteConfig {

    /**
     * Creates and configures the SQLite DataSource.
     * Creates the database directory if it doesn't exist and
     * sets up the connection to the SQLite database file.
     *
     * @return Configured SQLite DataSource
     * @throws RuntimeException if database directory creation fails
     */
    @Bean
    public DataSource dataSource() {
        try {
            Files.createDirectories(Path.of("src/main/resources/database"));
        } catch (Exception e) {
            throw new RuntimeException("Could not create database directory", e);
        }

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:src/main/resources/database/database.db");
        return dataSource;
    }
}