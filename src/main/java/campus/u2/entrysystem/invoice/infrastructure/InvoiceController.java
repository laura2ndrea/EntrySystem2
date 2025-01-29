package campus.u2.entrysystem.invoice.infrastructure;

import campus.u2.entrysystem.invoice.domain.Invoice;
import campus.u2.entrysystem.invoice.application.InvoiceService;
import campus.u2.entrysystem.membership.application.MembershipService;
import campus.u2.entrysystem.membership.domain.Membership;
import campus.u2.entrysystem.people.application.PeopleService;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.porters.application.PortersService;
import campus.u2.entrysystem.porters.domain.Porters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final PortersService portersService;
    private final PeopleService peopleService;
    private final MembershipService membershipService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, PortersService portersService, PeopleService peopleService, MembershipService membershipService) {
        this.invoiceService = invoiceService;
        this.portersService = portersService;
        this.peopleService = peopleService;
        this.membershipService = membershipService;
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }
    
    @PostMapping("/{idPeople}/people/{idPorters}/porters/{idMembership}/membership")
    public ResponseEntity<Invoice> createInvoice(@PathVariable String idPeople,
            @PathVariable String idPorters,
            @PathVariable String idMembership,
            @RequestBody Invoice invoice) {
        People people = peopleService.getPeopleById(idPeople);
        if (people == null) {
            return ResponseEntity.badRequest().build();
        }
        Porters porters = portersService.getPorterById(idPorters);
        Membership membership = membershipService.findMembershipById(idMembership);
        invoice.setPeople(people);
        invoice.setPorter(porters);
        invoice.setMembership(membership);
        Invoice savedInvoice = invoiceService.saveInvoice(invoice);
        return ResponseEntity.ok(savedInvoice);
    }
    
    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable String id) {
        return invoiceService.findInvoiceById(id);
    }
    
//http://localhost:8080/api/invoices/daterange?startDate=2025-01-01&endDate=2025-01-5 Ojo asi va este link 

    @GetMapping("/daterange")
    public ResponseEntity<List<Invoice>> getInvoicesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<Invoice> invoices = invoiceService.findInvoicesByDateRange(startDate, endDate);

        if (invoices.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(invoices);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable String id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Invoice> listAllInvoices() {
        return invoiceService.listAllInvoices();
    }
}
