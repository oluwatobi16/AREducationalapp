package backend.ar.app.model;

import jakarta.persistence.*;

@Entity
@Table(name="location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="location_id")
    private Integer id;

    @Column(name="latitude")
    private double latitude;
    @Column(name="longitude")
    private double longitude;

    public Location(){

    }

    public Location(Integer id, double latitude, double longitude) {
        this.id = id;

        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


}
