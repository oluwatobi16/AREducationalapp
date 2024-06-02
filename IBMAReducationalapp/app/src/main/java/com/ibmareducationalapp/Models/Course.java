
package com.ibmareducationalapp.Models;


public class Course {

    private int id;

    private String name;

    private String link;
    private String description;

    private Double averageRating;
    private Location location;

    public Course(){}
    public Course(int id ,String name,String link,String description,Location location,Double averageRating ){
        this.id=id;
        this.name=name;
        this.link=link;
        this.description=description;
        this.location= location;
        this.averageRating=averageRating;
    }
    public int getId(){return id;}
    public void setId(int id){
        this.id = id;
    }
    public String getName() {
        return name;}
    public void setName(String name) {
        this.name = name;
    }
    public String getLink() {
        return link;}
    public void setLink(String link) {
        this.link = link;
    }
    public String getDescription() {
        return description;}
    public void setDescription(String description) {
        this.description = description;
    }
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public double getAverageRating() {return averageRating;}

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
    @Override
    public String toString() {
        return name; // This is important for the spinner to display course names correctly
    }
}
