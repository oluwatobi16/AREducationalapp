
package backend.ar.app.model;


import jakarta.persistence.*;

@Entity
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="course_id")
    private int id;
    @Column(name="name")
    private String name;
    @Column(name="Link")
    private String link;
    @Column(name="Description")
    private String description;
    @Column(name = "average_rating")
    private Double averageRating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
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



    public void setAverageRating(Double averageRating) {this.averageRating=averageRating;
    }
}
