package campus.u2.entrysystem.vehicle.application;

import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.vehicle.application.VehicleRepository;
import campus.u2.entrysystem.vehicle.domain.Vehicle;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new GlobalException("Vehicle cannot be null");
        }
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle savePeopleVehicle(People people, Vehicle vehicle) {
        if (people == null) {
            throw new GlobalException("People cannot be null");
        }
        if (vehicle == null) {
            throw new GlobalException("Vehicle cannot be null");
        }
        vehicle.setPeople(people);
        people.addVehicle(vehicle);
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        if (id == null) {
            throw new GlobalException("ID cannot be null");
        }
        Optional<Vehicle> existingVehicleOpt = vehicleRepository.findById(id);
        if (existingVehicleOpt.isPresent()) {
            vehicleRepository.deleteById(existingVehicleOpt.get().getIdVehicle());
        } else {
            throw new GlobalException("Unexpected error, please try again");
        }

    }
    
    



    @Transactional
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    
    
    @Transactional
    public Optional<Vehicle> findVehicleByPlate(String plate) {
        if (plate == null || plate.isEmpty()) {
            throw new GlobalException("Plate cannot be null or empty");
        }
        return vehicleRepository.findByPlate(plate);
    }
    
    
     @Transactional
    public Optional<Vehicle> findbyId(Long id) {
        return vehicleRepository.findById(id);
    }
}
