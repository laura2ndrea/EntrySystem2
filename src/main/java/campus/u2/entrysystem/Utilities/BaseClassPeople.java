
package campus.u2.entrysystem.Utilities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;


    
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class BaseClassPeople {


@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id; 
private String name; 
private String cedula; 
private String telefono;

    public BaseClassPeople() {
    }

    public BaseClassPeople(String name, String cedula, String telefono) {
        this.name = name;
        this.cedula = cedula;
        this.telefono = telefono;
    }

    public BaseClassPeople(Long id, String name, String cedula, String telefono) {
        this.id = id;
        this.name = name;
        this.cedula = cedula;
        this.telefono = telefono;
    }
    


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "BaseClassPeople{" + "id=" + id + 
                ", name=" + name +
                ", cedula=" + cedula + 
                ", telefono=" + telefono + '}';
    }
    

    
}
