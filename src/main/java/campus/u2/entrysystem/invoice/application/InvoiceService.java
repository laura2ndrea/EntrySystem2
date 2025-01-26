package campus.u2.entrysystem.invoice.application;

import campus.u2.entrysystem.invoice.domain.Invoice;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
            throw new GlobalException("Invoice cannot be null.");
        }
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice findInvoiceById(Long idInvoice) {
        if (idInvoice == null) {
            throw new GlobalException("Invoice ID cannot be null.");
        }
        return invoiceRepository.findById(idInvoice)
                .orElseThrow(() -> new GlobalException("Invoice with ID " + idInvoice + " not found."));
    }

    @Transactional
    public List<Invoice> findInvoicesByDateRange(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }
        return invoiceRepository.findInvoicesByDateRange(startDate, endDate);
    }

    @Transactional
    public void deleteInvoice(Long idInvoice) {
        if (idInvoice == null) {
            throw new GlobalException("Invoice ID cannot be null.");
        }
        Invoice invoice = invoiceRepository.findById(idInvoice)
                .orElseThrow(() -> new GlobalException("Invoice with ID " + idInvoice + " not found."));
        invoiceRepository.delete(invoice);
    }

    public List<Invoice> listAllInvoices() {
        return invoiceRepository.findAll();
    }
}
