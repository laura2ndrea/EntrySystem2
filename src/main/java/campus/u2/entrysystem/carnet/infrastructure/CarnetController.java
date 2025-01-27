package campus.u2.entrysystem.carnet.infrastructure;

import campus.u2.entrysystem.carnet.application.CarnetService;
import campus.u2.entrysystem.carnet.domain.Carnet;
import campus.u2.entrysystem.people.application.PeopleService;
import campus.u2.entrysystem.people.domain.People;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carnet")
public class CarnetController {
    
    // Attributes 
    private final CarnetService carnetService; 
    private final PeopleService peopleService; 
    
    // Constructor 
    @Autowired 
    public CarnetController(CarnetService carnetService, PeopleService peopleService) {
        this.carnetService = carnetService; 
        this.peopleService = peopleService; 
    }
    
    // Methods 
    
    // To get all the carnets 
    @GetMapping
    public List<Carnet> getAllCarnets() {
        return carnetService.getAllCarnets();
    }
        
    // To get an access for the id 
    @GetMapping("/{id}")
    public Carnet getCarnetById(@PathVariable Long id) {
        return carnetService.getCarnetById(id); 
    }
        
    // To create a carnet 
    @PostMapping
    public Carnet createCarnet(@RequestBody Carnet carnet) {
        return carnetService.saveCarnet(carnet); 
    }
    
    // To save a carnet for a person 
    @PostMapping("/people/{idPeople}")
    public Carnet saveCarnetForPerson(@PathVariable Long idPeople, @RequestBody Carnet carnet) {
        People people = peopleService.getPeopleById(idPeople); 
        if (people != null) {
            return carnetService.saveCarnetForPerson(people, carnet);
        } else {
            return null; 
        }
    }
   
    

    @PostMapping("/{idpeople}/carnetupdate")
public ResponseEntity<?> AsignarCarnet(@PathVariable Long idpeople) {
    People peopleCarnet = peopleService.getPeopleById(idpeople);
    if (peopleCarnet == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Persona no encontrada");
    }
    Carnet carnet = new Carnet();
    carnet.setPeople(peopleCarnet);
    peopleCarnet.setCarnet(carnet);
//    peopleService.savePeople(peopleCarnet); // Asegúrate de guardar también a la persona actualizada
    carnetService.saveCarnet(carnet);
    return ResponseEntity.ok(carnet);
}

    
    
    
    
    
    
    
    
    
    // To find a carnet by id people 
    @GetMapping("/people/{idPeople}")
    public Carnet findCarnetByPeople(@PathVariable Long idPeople) {
        People people = peopleService.getPeopleById(idPeople); 
        if (people != null && people.getCarnet() != null) {
            return carnetService.getCarnetById(people.getCarnet().getId());
        }
        return null; 
    }
    
    // To update the status of the carnet 
    @PutMapping("/{carnetId}")
    public Carnet updateCarnetStatus(@PathVariable Long idCarnet, @RequestParam Boolean newStatus){
        Carnet carnet = carnetService.getCarnetById(idCarnet); 
        if (carnet != null) { 
            carnet.setStatus(newStatus);
            return carnetService.saveCarnet(carnet);
        }
        return null; 
    }
    
}
