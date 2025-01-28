package campus.u2.entrysystem.vehicle.infrastructure;

import campus.u2.entrysystem.people.application.PeopleService;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.vehicle.application.VehicleService;
import campus.u2.entrysystem.vehicle.domain.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;
    private final PeopleService peopleService;

    @Autowired
    public VehicleController(VehicleService vehicleService, PeopleService peopleService) {
        this.vehicleService = vehicleService;
        this.peopleService = peopleService;
    }

//funciona
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }
//funciona

    @GetMapping("/{idVehicle}")
    public Optional<Vehicle> findbyId(@PathVariable Long idVehicle) {
        return vehicleService.findbyId(idVehicle);
    }
//funciona

    @GetMapping("/plate/{plate}")
    public Optional<Vehicle> findByPlate(@PathVariable String plate) {
        return vehicleService.findVehicleByPlate(plate);
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.saveVehicle(vehicle));
    }
// funciona

    @DeleteMapping("/{idVehicle}")
    public void deleteVehicle(@PathVariable Long idVehicle) {
        vehicleService.deleteVehicle(idVehicle);
    }

    @PostMapping("/{personId}")
    public ResponseEntity<People> addVehicleToPerson(@PathVariable Long personId, @RequestBody Vehicle vehicle) {
        People people = peopleService.getPeopleById(personId);
        if (people == null) {
            return ResponseEntity.notFound().build();
        }
        people.addVehicle(vehicle);

        peopleService.savePeople(people);

        return ResponseEntity.ok(people);
    }

    @PutMapping("/{idVehicle}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long idVehicle, @RequestBody Vehicle vehicle) {
        Optional<Vehicle> existingVehicleOpt = vehicleService.findbyId(idVehicle);
        if (existingVehicleOpt.isPresent()) {
            Vehicle existingVehicle = existingVehicleOpt.get();

            if (vehicle.getPlate() != null) {
                existingVehicle.setPlate(vehicle.getPlate());
            }
            existingVehicle.setVehicleType(vehicle.getVehicleType());

            Vehicle updatedVehicle = vehicleService.saveVehicle(existingVehicle);
            return ResponseEntity.ok(updatedVehicle);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
