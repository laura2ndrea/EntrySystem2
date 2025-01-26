package campus.u2.entrysystem.people.domain;

import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import campus.u2.entrysystem.vehicle.domain.Vehicle;
import campus.u2.entrysystem.invoice.domain.Invoice;
import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.carnet.domain.Carnet;
import campus.u2.entrysystem.Utilities.BaseClassPeople;
import campus.u2.entrysystem.access.domain.Access;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;

@Entity
public class People extends BaseClassPeople {

    private Boolean personType;
    
    @JsonBackReference("company-people")
    @ManyToOne(cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_company", nullable = true)
    private Company company;
    
    @JsonManagedReference("invoice-people")
    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Invoice> invoices = new ArrayList<>();

    @JsonManagedReference("equipment-people")
    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<RegisteredEquipment> equipments = new ArrayList<>();
    
    @JsonManagedReference("vehicle-people")
    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Vehicle> vehicles = new ArrayList<>();
    
    @JsonManagedReference("carnet-people")
    @OneToOne(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Carnet carnet;
    
    //@JsonBackReference("people-access")
    @JsonIdentityReference(alwaysAsId = true)
    @OneToMany(mappedBy = "people", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Access> access = new ArrayList<>();

    public People() {
    }

    public People(Boolean personType, String name, String cedula, String telefono) {
        super(name, cedula, telefono);
        this.personType = personType;
    }

    public People(Boolean personType, Company company, String name, String cedula, String telefono) {
        super(name, cedula, telefono);
        this.personType = personType;
        this.company = company;
    }

    public People(Boolean personType, Long id, String name, String cedula, String telefono) {
        super(id, name, cedula, telefono);
        this.personType = personType;
    }

    public People(Boolean personType, Company company, Long id, String name, String cedula, String telefono) {
        super(id, name, cedula, telefono);
        this.personType = personType;
        this.company = company;
    }

    public Boolean getPersonType() {
        return personType;
    }

    public void setPersonType(Boolean personType) {
        this.personType = personType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setPeople(this);
    }

    public void removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setPeople(null);
    }

    public List<RegisteredEquipment> getEquipments() {
        return equipments;
    }

    public void addEquipments(RegisteredEquipment equipments) {
        this.equipments.add(equipments);
        equipments.setPeople(this);
    }

    public void removeEquiments(RegisteredEquipment equipments) {
        this.equipments.remove(equipments);
        equipments.setPeople(null);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
        vehicle.setPeople(this);
    }

    public void removeVehicles(Vehicle vehicle) {
        this.vehicles.remove(vehicle);
        vehicle.setPeople(null);
    }

    public Carnet getCarnet() {
        return carnet;
    }

    public void setCarnet(Carnet carnet) {
        this.carnet = carnet;
    }

    @Override
    public String toString() {
        return "People{" + "personType=" + personType
                + ", company=" + company + ", carnet=" + carnet + '}';
    }

}
