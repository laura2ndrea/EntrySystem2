package campus.u2.entrysystem.porters.infrastructure;

import campus.u2.entrysystem.porters.application.PortersService;
import campus.u2.entrysystem.porters.domain.Porters;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/porters")
public class PortersController {

    // Attributes 
    private final PortersService portersService;

    // Constructor 
    @Autowired
    public PortersController(PortersService portersService) {
        this.portersService = portersService;
    }

    // Methods 
    // To save a porter 
    @PostMapping
    public Porters savePorter(@RequestBody Porters porter) {
        return portersService.savePorter(porter);
    }

    // To add a boss to a porter 
    @PutMapping("/{idPorter}/boss/{idBoss}")
    public Porters addBossToPorter(@PathVariable Long idPorter, @PathVariable Long idBoss) {
        return portersService.addBossToPorter(idPorter, idBoss);
    }

    // To delete a porter
    @DeleteMapping("/{id}")
    public void deletePorter(@PathVariable Long id) {
        portersService.deletePorter(id);
    }

    // To list all porters 
    @GetMapping
    public List<Porters> listAllPorters() {
        return portersService.listAllPorters();
    }

    // To get a porter by id 
    @GetMapping("/{id}")
    public Porters getPorterById(@PathVariable Long id) {
        return portersService.getPorterById(id);
    }

    // To get porters depending on the position 
    @GetMapping("/position")
    public List<Porters> getPortersByPosition(@RequestParam Boolean position) {
        return portersService.getPortersByPosition(position);
    }

    // To update a porter by id 
    @PutMapping("/{id}")
    public Porters updatePorter(@PathVariable Long id, @RequestBody Porters newPorter) {
        Porters porter = portersService.getPorterById(id);
        if (porter != null) {
            if (newPorter.getName() != null) {
                porter.setName(newPorter.getName());
            }
            if (newPorter.getCedula() != null) {
                porter.setCedula(newPorter.getCedula());
            }
            if (newPorter.getTelefono() != null) {
                porter.setTelefono(newPorter.getTelefono());
            }
            if (newPorter.getEmploymentDate()!= null) {
                porter.setEmploymentDate(newPorter.getEmploymentDate());
            }
            if (newPorter.getPosition() != null) {
                porter.setPosition(newPorter.getPosition());
            }
            if (newPorter.getId_jefe()!= null) {
                porter.setId_jefe(newPorter.getId_jefe());
            }
            return portersService.savePorter(porter); 
        } else {
            return portersService.savePorter(newPorter); 
        }
    }
}
