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
    public Vehicle findyId(@PathVariable String idVehicle) {
        return vehicleService.findById(idVehicle);
    }
//funciona

    @GetMapping("/plate/{plate}")
    public Vehicle findByPlate(@PathVariable String plate) {
        return vehicleService.findVehicleByPlate(plate);
    }

    @PostMapping
    public ResponseEntity<Vehicle> createVehicle(@RequestBody Vehicle vehicle) {
        return ResponseEntity.ok(vehicleService.saveVehicle(vehicle));
    }
// funciona

    @DeleteMapping("/{idVehicle}")
    public void deleteVehicle(@PathVariable String idVehicle) {
        vehicleService.deleteVehicle(idVehicle);
    }

    @PostMapping("/{personId}")
    public ResponseEntity<People> addVehicleToPerson(@PathVariable String personId, @RequestBody Vehicle vehicle) {
        People people = peopleService.getPeopleById(personId);
        if (people == null) {
            return ResponseEntity.notFound().build();
        }
        people.addVehicle(vehicle);

        peopleService.savePeople(people);

        return ResponseEntity.ok(people);
    }

    @PutMapping("/{idVehicle}")
    public ResponseEntity<Vehicle> updatesVehicle(@PathVariable String idVehicle, @RequestBody Vehicle vehicle) {
        Vehicle existingVehicle = vehicleService.findById(idVehicle);

        if (vehicle.getPlate() != null && !vehicle.getPlate().isBlank()) {
            existingVehicle.setPlate(vehicle.getPlate());
        }
        existingVehicle.setVehicleType(vehicle.getVehicleType());
        Vehicle updatedVehicle = vehicleService.saveVehicle(existingVehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

}
