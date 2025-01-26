package campus.u2.entrysystem.porters.infrastructure;

import campus.u2.entrysystem.porters.domain.Porters;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PortersJpaRepository extends JpaRepository<Porters, Long> {

    List<Porters> findByPosition(Boolean position);

}
