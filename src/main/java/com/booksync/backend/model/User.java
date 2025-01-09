package com.booksync.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name  = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Date dateJoined;

    @Column(nullable = false)
    private boolean isValidated;

    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public Date getDateJoined() {
        return dateJoined;
    }
    public boolean isValidated() {
        return isValidated;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }
    public void setValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }

}


