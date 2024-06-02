package com.ibmareducationalapp.Models;


public class Armarker {
    private int id;

    private String description;
    private Location location;

    public Armarker(){

    }
    public Armarker(int id,String Description,Location location){
        this.id=id;
        this.description= description;
        this.location=location;
    }
    public int getId(){return id;}
    public void setId(int id){this.id =id;}
    public String getDescription(){return description;}
    public void setDescription(String description){
        this.description=description;
    }
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }


}
