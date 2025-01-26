package campus.u2.entrysystem.access.infrastructure;

import campus.u2.entrysystem.access.domain.Access;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessJpaRepository extends JpaRepository<Access, Long> {
    
    List<Access> findByEntryAccessBetween(Date startDate, Date endDate);
}
