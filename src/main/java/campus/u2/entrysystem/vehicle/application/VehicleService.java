package campus.u2.entrysystem.vehicle.application;

import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.vehicle.application.VehicleRepository;
import campus.u2.entrysystem.vehicle.domain.Vehicle;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import campus.u2.entrysystem.people.application.PeopleRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final PeopleRepository peopleRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository, PeopleRepository peopleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.peopleRepository = peopleRepository; 
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

//    @Transactional
//    public void deleteVehicle(Long id) {
//        if (id == null) {
//            throw new GlobalException("ID cannot be null");
//        }
//        Optional<Vehicle> existingVehicleOpt = vehicleRepository.findById(id);
//        if (existingVehicleOpt.isPresent()) {
//            vehicleRepository.deleteById(existingVehicleOpt.get().getIdVehicle());
//            entityManager.flush();
//        } else {
//            throw new GlobalException("Unexpected error, please try again");
//        }
//
//    }
@Transactional
    public void deleteVehicle(Long id) {

        
// Obtén el vehículo que quieres eliminar
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
            Vehicle vehicle = vehicleOptional.get();
            Optional<People> peopleOpt = peopleRepository.getPeopleById(vehicle.getPeople().getId()); 
            People people = peopleOpt.get(); 
            people.getVehicles().remove(vehicle);
            vehicle.setPeople(null);
            
//            // Quita la referencia a 'people' si es necesario
//            vehicle.getPeople()
//                vehicle.setPeople(null);  // Esto quita la referencia
//        
//
//            // Asegúrate de hacer flush
//            entityManager.flush(); // Sincroniza el contexto de persistencia antes de eliminar

            // Luego, elimina el vehículo
            vehicleRepository.deleteById(id);
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
