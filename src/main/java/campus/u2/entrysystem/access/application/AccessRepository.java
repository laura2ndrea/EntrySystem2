package campus.u2.entrysystem.access.application;

import campus.u2.entrysystem.access.domain.Access;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AccessRepository {

    Access saveAccess(Access access);
    void deleteAccess(Long id);
    List<Access> getAllAccesses();
    Optional<Access> getAccessById(Long id);
    List<Access> findAccessBetweenDates(Date startDate, Date endDate);

}
