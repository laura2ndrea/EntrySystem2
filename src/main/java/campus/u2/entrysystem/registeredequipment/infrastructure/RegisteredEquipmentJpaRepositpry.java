
package campus.u2.entrysystem.registeredequipment.infrastructure;

import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RegisteredEquipmentJpaRepositpry  extends JpaRepository<RegisteredEquipment, Long>{
    
}
