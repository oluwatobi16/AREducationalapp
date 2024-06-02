package backend.ar.app.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Authentication")
public class Authentication {
    @Id
    private int userId;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
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
