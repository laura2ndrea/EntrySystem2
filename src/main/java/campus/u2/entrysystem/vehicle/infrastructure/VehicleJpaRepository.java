package campus.u2.entrysystem.vehicle.infrastructure;

import campus.u2.entrysystem.vehicle.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleJpaRepository extends JpaRepository<Vehicle, Long> {

    Optional<Vehicle> findByPlate(String plate);


}
