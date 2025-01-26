package campus.u2.entrysystem.invoice.infrastructure;

import campus.u2.entrysystem.invoice.application.InvoiceRepository;
import campus.u2.entrysystem.invoice.domain.Invoice;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class InvoiceAdapter implements InvoiceRepository {
    
    private final InvoiceJpaRepositoy invoiceJpaRepositoy;
    
    public InvoiceAdapter(InvoiceJpaRepositoy invoiceJpaRepositoy) {
        this.invoiceJpaRepositoy = invoiceJpaRepositoy;
    }
    
    @Override
    public Invoice save(Invoice invoice) {        
        return invoiceJpaRepositoy.save(invoice);
        
    }
    
    @Override
    public Optional<Invoice> findById(Long idInvoice) {        
        return invoiceJpaRepositoy.findById(idInvoice);
    }
    
    @Override
    public List<Invoice> findInvoicesByDateRange(Date startDate, Date endDate) {
        return invoiceJpaRepositoy.findInvoicesByDateRange(startDate, endDate);
    }
    
    @Override
    public void delete(Invoice invoice) {
        
        Optional<Invoice> optionalInvoice = invoiceJpaRepositoy.findById(invoice.getIdInvoice());
        if (optionalInvoice.isPresent()) {
            invoiceJpaRepositoy.delete(optionalInvoice.get());
        }
        
    }
    
    
    
    @Override
    public List<Invoice> findAll() {
        
        return invoiceJpaRepositoy.findAll();
    }
    
}
