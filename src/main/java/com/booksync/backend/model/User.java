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
    private String password;

    @Column(nullable = false)
    private Date dateJoined;

    @Column(nullable = false)
    private boolean isValidated;

    @Column(nullable = false)
    private Role roleType;

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
    public String getPassword() {
        return password;
    }
    public Date getDateJoined() {
        return dateJoined;
    }
    public boolean isValidated() {
        return isValidated;
    }
    public Role getRoleType() {
        return roleType;
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
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }
    public void setValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }
    public void setRoleType(Role roleType) {
        this.roleType = roleType;
    }
}


