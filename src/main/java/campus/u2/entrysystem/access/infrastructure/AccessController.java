package campus.u2.entrysystem.access.infrastructure;

import campus.u2.entrysystem.access.application.AccessService;
import campus.u2.entrysystem.access.domain.Access;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import campus.u2.entrysystem.porters.application.PortersService;
import campus.u2.entrysystem.porters.domain.Porters;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import java.util.Date;
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
@RequestMapping("/api/access")
public class AccessController {

    // Attributes 
    private final AccessService accessService;
    private final PortersService porterService;

    // Constructor 
    @Autowired
    public AccessController(AccessService accessService, PortersService porterService) {
        this.accessService = accessService;
        this.porterService = porterService;
    }

    // Methods 
    // To get all the access 
    @GetMapping
    public List<Access> getAllAccess() {
        return accessService.getAllAccesses();
    }

    // To get an access for the id 
    @GetMapping("/{id}")
    public Access getAccessById(@PathVariable Long id) {
        return accessService.getAccessById(id);
    }

    // To create a new access 
    @PostMapping
    public Access createAccess(@RequestBody Access acccess) {
        return accessService.saveAccess(acccess);
    }

    // To delete an access 
    @DeleteMapping("/{id}")
    public void deleteAccess(@PathVariable Long id) {
        accessService.deleteAccess(id);
    }

    //To add a note to an access 
    @PostMapping("/add-note/{idAccess}")
    public Access addAccessNoteToAccess(@PathVariable Long idAccess, @RequestBody AccessNote accessNote) {
        return accessService.addAccessNoteToAccess(idAccess, accessNote);
    }

    // To add a porter to an access
    @PostMapping("/{id}/porters/{idPorter}")
    public Access addPorterToAccess(@PathVariable Long id, @PathVariable Long idPorter) {
        return accessService.addPorterToAccess(id, idPorter);
    }

    // To delete a porter from an access 
    @DeleteMapping("/{id}/porters/{idPorter}")
    public Access removePorterFromAccess(@PathVariable Long id, @PathVariable Long idPorter) {
        return accessService.removePorterFromAccess(id, idPorter);
    }

    // To get al the access between two dates 
    @GetMapping("/dates")
    public List<Access> findAccessBetweenDates(@RequestParam Date startDate, @RequestParam Date endDate) {
        return accessService.findAccessBetweenDates(startDate, endDate);
    }

    // To update a porter in an access 
    @PutMapping("/{idAccess}/porters/{idPorter}")
    public Access updatePorterInAccess(@PathVariable Long idAccess, @PathVariable Long idPorter) {
        Access access = accessService.getAccessById(idAccess);
        if (access == null) {
            throw new GlobalException("Access with id " + idAccess + " not found");
        }
        Porters porter = porterService.getPorterById(idPorter);
        if (porter == null) {
            // Si no se encuentra el portero, lanzar una excepción o retornar un error
            throw new GlobalException("Porter with id " + idPorter + " not found");
        }
        access.getPorters().add(porter); 
        return accessService.saveAccess(access);
    }
}
