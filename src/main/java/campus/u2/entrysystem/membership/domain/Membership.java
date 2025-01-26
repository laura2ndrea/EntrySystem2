package campus.u2.entrysystem.membership.domain;

import campus.u2.entrysystem.invoice.domain.Invoice;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMembership;

    private Integer duration;
    private Double price;
    private Boolean vehicleType;
    
    @JsonManagedReference("invoice-membership")
    @OneToMany(mappedBy = "membership", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Invoice> invoices = new ArrayList<>();

    public Membership() {
    }

    public Membership(Integer duration, Double price, Boolean vehicleType) {
        this.duration = duration;
        this.price = price;
        this.vehicleType = vehicleType;
    }

    public Long getIdMembership() {
        return idMembership;
    }

    public void setIdMembership(Long idMembership) {
        this.idMembership = idMembership;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Boolean vechicleType) {
        this.vehicleType = vechicleType;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setMembership(this);
    }

    public void removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setMembership(null);
    }

    @Override
    public String toString() {
        return "Membership{" + "idMembership=" + idMembership
                + ", duration=" + duration
                + ", price=" + price
                + ", vehicleType=" + vehicleType
                + '}';
    }

}
