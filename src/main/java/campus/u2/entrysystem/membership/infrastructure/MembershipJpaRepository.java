package campus.u2.entrysystem.membership.infrastructure;


import campus.u2.entrysystem.membership.domain.Membership;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipJpaRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByDuration(Integer duration);

    List<Membership> findByPrice(Double price);

    List<Membership> findByPriceLessThan(Double price);
}
