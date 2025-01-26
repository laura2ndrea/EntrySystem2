package campus.u2.entrysystem.vehicle.infrastructure;

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

    @Autowired
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
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
    @DeleteMapping("{idVehicle}")
    public void deleteVehicle(@PathVariable Long idVehicle) {
        vehicleService.deleteVehicle(idVehicle);
    }
    
    
  @PutMapping
public ResponseEntity<Vehicle> updateVehicle(@RequestBody Vehicle vehicle) {
    Optional<Vehicle> existingVehicleOpt = vehicleService.findbyId(vehicle.getIdVehicle());
    if (existingVehicleOpt.isPresent()) {
        Vehicle updatedVehicle = vehicleService.saveVehicle(vehicle);
        return ResponseEntity.ok(updatedVehicle);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}

    
    
    
}
