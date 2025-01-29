package campus.u2.entrysystem.vehicle.infrastructure;

import campus.u2.entrysystem.vehicle.application.VehicleRepository;
import campus.u2.entrysystem.vehicle.domain.Vehicle;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleAdapter implements VehicleRepository {

    private final VehicleJpaRepository vehicleJpaRepository;

    @Autowired
    public VehicleAdapter(VehicleJpaRepository vehicleJpaRepository) {
        this.vehicleJpaRepository = vehicleJpaRepository;
    }

    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) {

        return vehicleJpaRepository.save(vehicle);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (vehicleJpaRepository.existsById(id)) {
            vehicleJpaRepository.deleteById(id);
        } 
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleJpaRepository.findAll();
       
    }

    @Override
    public Optional<Vehicle> findByPlate(String plate) {
       return vehicleJpaRepository.findByPlate(plate);
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
       return vehicleJpaRepository.findById(id);
    }

}
