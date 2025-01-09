package com.booksync.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
public class SQLiteConfig {

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