package campus.u2.entrysystem.carnet.domain;

import campus.u2.entrysystem.people.domain.People;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity 
public class Carnet {
    // Atributtes 
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long idCarnet;
    
    @Column( unique = true , nullable = true)
    private String code; 
    
    @Column(nullable = false)
    private Boolean status;
    
    @JsonBackReference("carnet-people")
    @OneToOne
    @JoinColumn(name = "id_people")
    private People people; 
    
    // Constructor

    public Carnet() {
        
        this.status = true;
    }

    
    // Getters and setters 
    public Long getId(){
        return idCarnet; 
    }
    
    public void setId(Long idCarnet) {
        this.idCarnet = idCarnet; 
    }
    
    public String getCode(){
        return code; 
    }
    
    public void setCode(String code){
        this.code = code; 
    }
    
    public boolean getStatus(){
        return status; 
    }
    
    public void setStatus(Boolean status){
        this.status = status; 
    }
    
    public People getPeople (){
        return people; 
    }
    
    public void setPeople (People people) {
        this.people = people; 
        generateCode();
    }
    
    // Methods 
    @Override
    public String toString() {
        return "Carnet{" + "idCarnet=" + idCarnet + ", code=" + code + ", status=" + status +  '}';
    }
    
    private String generateCode(){
        if(idCarnet == null || people == null || people.getCedula() == null) {
            throw new IllegalStateException("Cannot generate code: idCarnet or people.id is null"); 
        }
        this.code = idCarnet + String.valueOf(people.getCedula());

        return   this.code;
    }
}

