package backend.ar.app.model;

import jakarta.persistence.*;

@Entity
@Table(name="armaker")
public class Armarker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="marker_Id")
    private int id;
    @Column(name="description")
    private String description;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
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
