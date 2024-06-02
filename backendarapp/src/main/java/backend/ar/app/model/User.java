package backend.ar.app.model;
import jakarta.persistence.*;


@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_Id")
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "academic_subject")
    private String academicSubject;
    @Column(name = "university_email")
    private String email;

    public User(){

    }
    public User(int id ,String name,String academicSubject,String email){
        this.id = id;
        this.name = name;
        this.academicSubject = academicSubject;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }


    public String getAcademicSubject() {
        return academicSubject;
    }
    public void setAcademicSubject(String academicSubject ){
        this.academicSubject = academicSubject;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

}
