package campus.u2.entrysystem.invoice.infrastructure;


import campus.u2.entrysystem.invoice.domain.Invoice;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InvoiceJpaRepositoy extends JpaRepository<Invoice, Long> {

    List<Invoice> findByDate(Date date);

    @Query("SELECT i FROM Invoice i WHERE i.date BETWEEN :startDate AND :endDate")
    List<Invoice> findInvoicesByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
