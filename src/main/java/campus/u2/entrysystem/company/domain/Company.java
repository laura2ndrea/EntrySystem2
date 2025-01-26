package campus.u2.entrysystem.company.domain;

import campus.u2.entrysystem.people.domain.People;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import java.util.*;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id_company;
    private String name;
    
    @JsonManagedReference("company-people")
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<People> peopleList = new ArrayList<>();

    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }

    public Company(Long id_company, String name) {
        this.id_company = id_company;
        this.name = name;
    }

    public Long getId_company() {
        return id_company;
    }

    public void setId_company(Long id_company) {
        this.id_company = id_company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<People> getPeopleList() {
        return peopleList;
    }

    public void addPeople(People peopleList) {
        this.peopleList.add(peopleList);
    }
    
    public void removePeople(People peopleList) {
        this.peopleList.remove(peopleList);
    }

    @Override
    public String toString() {
        return "Company{" + "id_company=" + id_company + 
                ", name=" + name +
                ", peopleList=" + peopleList + '}';
    }

   
    

    
    

}
