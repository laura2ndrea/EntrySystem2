package campus.u2.entrysystem.invoice.infrastructure;

import campus.u2.entrysystem.invoice.domain.Invoice;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.invoice.application.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.findInvoiceById(id);
    }

    @GetMapping("/daterange")
    public List<Invoice> getInvoicesByDateRange(@RequestParam Date startDate, @RequestParam Date endDate) {
        return invoiceService.findInvoicesByDateRange(startDate, endDate);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }

    @GetMapping
    public List<Invoice> listAllInvoices() {
        return invoiceService.listAllInvoices();
    }
}