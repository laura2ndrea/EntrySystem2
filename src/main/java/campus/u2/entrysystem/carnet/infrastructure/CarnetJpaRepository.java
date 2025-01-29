package campus.u2.entrysystem.carnet.infrastructure;


import campus.u2.entrysystem.carnet.domain.Carnet;
import campus.u2.entrysystem.people.domain.People;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarnetJpaRepository extends JpaRepository<Carnet, Long>{
    
    Optional<Carnet> findByPeople(People people);
    boolean existsByCode(String code);
    
}
