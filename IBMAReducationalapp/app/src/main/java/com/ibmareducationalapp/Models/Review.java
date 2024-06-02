package com.ibmareducationalapp.Models;

public class Review {
    private int id;
    private int rating;
    private String reviewText;
    private User user;
    private Course course;
    public Review() {
        // Default constructor
    }

    public Review(int rating, String reviewText,Course course,User user) {

        this.rating = rating;
        this.reviewText = reviewText;
        this.user=user;
        this.course = course;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


}
