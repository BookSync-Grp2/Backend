package com.booksync.backend.dto;

import com.booksync.backend.model.Role;
import com.booksync.backend.model.User;

import java.util.Date;

public class UserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date dateJoined;
    private boolean isValidated;
    private Role roleType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public boolean isValidated() {
        return isValidated;
    }

    public void setValidated(boolean validated) {
        isValidated = validated;
    }

    public Role getRoleType() {
        return roleType;
    }

    public void setRoleType(Role roleType) {
        this.roleType = roleType;
    }

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setDateJoined(user.getDateJoined());
        dto.setValidated(user.isValidated());
        dto.setRoleType(user.getRoleType());
        return dto;
    }
}