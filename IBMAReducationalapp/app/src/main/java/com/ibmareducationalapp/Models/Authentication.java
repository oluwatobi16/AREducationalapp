package com.ibmareducationalapp.Models;


public class Authentication {

    private int userId;


    private String password;


    private User user;

    // Default constructor
    public Authentication() {
    }

    // Parameterized constructor for all fields
    public Authentication(User user, String password) {
        this.user = user;
        this.password = password;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
