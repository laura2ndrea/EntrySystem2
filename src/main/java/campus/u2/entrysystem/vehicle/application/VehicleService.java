package campus.u2.entrysystem.vehicle.application;

import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.vehicle.application.VehicleRepository;
import campus.u2.entrysystem.vehicle.domain.Vehicle;
import campus.u2.entrysystem.Utilities.exceptions.InvalidInputException;
import campus.u2.entrysystem.Utilities.exceptions.NotFoundException;
import campus.u2.entrysystem.Utilities.exceptions.TypeMismatchException;
import campus.u2.entrysystem.Utilities.exceptions.UniqueViolationException;
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
            throw new InvalidInputException("Vehicle cannot be null");
        }
        return vehicleRepository.save(vehicle);
    }

    @Transactional
    public Vehicle savePeopleVehicle(People people, Vehicle vehicle) {
        if (people == null) {
            throw new InvalidInputException("People cannot be null");
        }
        if (vehicle == null) {
            throw new InvalidInputException("Vehicle cannot be null");
        }

        Optional<People> existingPerson = peopleRepository.getPeopleById(people.getId());
        if (existingPerson.isEmpty()) {
            throw new NotFoundException("Person with ID " + people.getId() + " not found");
        }

        Optional<Vehicle> existingVehicle = vehicleRepository.findByPlate(vehicle.getPlate());
        if (existingVehicle.isPresent()) {
            throw new UniqueViolationException("A vehicle with plate " + vehicle.getPlate() + " already exists");
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
    public void deleteVehicle(String idVehicle) {
        if (idVehicle == null || idVehicle.isBlank()) {
            throw new InvalidInputException("Vehicle ID cannot be null or empty.");
        }
        try {
            Long id = Long.parseLong(idVehicle);
            Vehicle vehicle = vehicleRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Vehicle with ID " + id + " not found"));

            People people = peopleRepository.getPeopleById(vehicle.getPeople().getId())
                    .orElseThrow(() -> new NotFoundException("Owner of vehicle with ID " + id + " not found"));

            people.getVehicles().remove(vehicle);
            vehicle.setPeople(null);

            entityManager.flush();
            vehicleRepository.deleteById(id);
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("idVehicle", "Long", "Invalid format: " + idVehicle);
        }
    }
    
    
    @Transactional
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    
    @Transactional
    public Vehicle findVehicleByPlate(String plate) {
        if (plate == null || plate.isEmpty()) {
            throw new InvalidInputException("Plate cannot be null or empty");
        }
        return vehicleRepository.findByPlate(plate)
                .orElseThrow(() -> new NotFoundException("Vehicle with plate " + plate + " not found"));
    }
    
    
    @Transactional
    public Vehicle findById(String id) {
        if (id == null || id.isBlank()) {
            throw new InvalidInputException("Vehicle ID cannot be null or empty.");
        }
        try {
            Long idVehicle = Long.parseLong(id);
            return vehicleRepository.findById(idVehicle)
                    .orElseThrow(() -> new NotFoundException("Vehicle with ID " + idVehicle + " not found"));
        } catch (NumberFormatException ex) {
            throw new TypeMismatchException("id", "Long", "Invalid format: " + id);
        }
    }
}
