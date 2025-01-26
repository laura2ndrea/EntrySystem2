package campus.u2.entrysystem.membership.application;

import campus.u2.entrysystem.membership.domain.Membership;
import java.util.List;
import java.util.Optional;

public interface MembershipRepository {
    
    Membership save(Membership membership);
    Optional<Membership> findById(Long idMembership); 
    List<Membership> findAll(); 
    void delete(Membership membership); 
//    boolean existsById(Long idMembership); Comentado por que no se esta usando no se para que esta el service no lo usa 
    
    List<Membership> findByDuration(Integer duration);
    List<Membership> findByPrice(Double price); 
    List<Membership> findByPriceLessThan(Double price); 
}
