package campus.u2.entrysystem.invoice.application;

import campus.u2.entrysystem.invoice.domain.Invoice;
import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public Invoice saveInvoice(Invoice invoice) {
        if (invoice == null) {
            throw new InvalidInputException("Invoice cannot be null.");
        }
        return invoiceRepository.save(invoice);
    }

    @Transactional
      public Invoice findInvoiceById(String idInvoice) {
        if (idInvoice == null || idInvoice.isEmpty()) {
            throw new InvalidInputException("Invoice ID cannot be null or empty.");
        }
        Long invoiceId;
        try {
            invoiceId = Long.parseLong(idInvoice);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid Invoice ID format: " + idInvoice);
        }
        return invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice with ID " + invoiceId + " not found."));
    }

    @Transactional
    public List<Invoice> findInvoicesByDateRange(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new InvalidInputException("Start date and end date must not be null.");
        }
        if (startDate.after(endDate)) {
            throw new InvalidInputException("Start date must be before or equal to end date.");
        }
        return invoiceRepository.findInvoicesByDateRange(startDate, endDate);
    }

    @Transactional
    public void deleteInvoice(String idInvoice) {
        if (idInvoice == null || idInvoice.isEmpty()) {
            throw new InvalidInputException("Invoice ID cannot be null or empty.");
        }
        Long invoiceId;
        try {
            invoiceId = Long.parseLong(idInvoice);
        } catch (NumberFormatException e) {
            throw new TypeMismatchException("id", "Long", "Invalid Invoice ID format: " + idInvoice);
        }
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException("Invoice with ID " + invoiceId + " not found."));
        invoiceRepository.delete(invoice);
    }
    
    @Transactional
    public List<Invoice> listAllInvoices() {
        return invoiceRepository.findAll();
    }
}
