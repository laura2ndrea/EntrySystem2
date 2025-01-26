package campus.u2.entrysystem.invoice.application;

import campus.u2.entrysystem.invoice.domain.Invoice;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface InvoiceRepository {

    Invoice save(Invoice invoice);
    Optional<Invoice> findById(Long idInvoice); 
    List<Invoice> findInvoicesByDateRange(Date startDate, Date endDate);
    void delete(Invoice invoice); 
    List<Invoice> findAll(); 

}
