package campus.u2.entrysystem.vehicle.application;

import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.vehicle.domain.Vehicle;
import java.util.List;
import java.util.Optional;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    void deleteById(Long id);

  

    List<Vehicle> findAll();

    Optional<Vehicle> findByPlate(String plate);

    Optional<Vehicle> findById(Long id);
}
