package campus.u2.entrysystem.user.domain;

import campus.u2.entrysystem.porters.domain.Porters;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    @JsonBackReference("porter-user")
    @OneToOne
    @JoinColumn(name = "id_porters", foreignKey = @ForeignKey(name = "FK_user_porters"), nullable = true)
    private Porters porter;

    /// Constructor
    public User() {
    }

    public User(String userName, String password, Porters porters) {
        this.userName = userName;
        this.password = password;
        this.porter = porters;
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Porters getPorter() {
        return porter;
    }

    public void setPorter(Porters porter) {
        this.porter = porter;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", password=" + password + ", idPorter=" + porter + '}';
    }

}
