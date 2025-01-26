package campus.u2.entrysystem.registeredequipment.domain;

import campus.u2.entrysystem.people.domain.People;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class RegisteredEquipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String serial;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date registrationDate;

    @Column(nullable = true)
    private String description;

    @JsonBackReference("equipment-people")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_people", nullable = false)
    private People people;

    public RegisteredEquipment() {
    }

    public RegisteredEquipment(String serial, Date registrationDate, String description) {
        this.serial = serial;
        this.registrationDate = registrationDate;
        this.description = description;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "RegisteredEquipment{"
                + "id=" + id
                + ", serial=" + serial
                + ", registrationDate=" + registrationDate
                + ", description=" + description
                + '}';
    }

}
