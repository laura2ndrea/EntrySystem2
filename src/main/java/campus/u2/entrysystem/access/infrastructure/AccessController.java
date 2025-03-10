package campus.u2.entrysystem.access.infrastructure;

import campus.u2.entrysystem.access.application.AccessService;
import campus.u2.entrysystem.access.domain.Access;
import campus.u2.entrysystem.accessnotes.domain.AccessNote;
import campus.u2.entrysystem.people.application.PeopleService;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.porters.application.PortersService;
import campus.u2.entrysystem.porters.domain.Porters;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final PeopleService peopleService;

    // Constructor 
    @Autowired
    public AccessController(PeopleService peopleService, AccessService accessService, PortersService porterService) {
        this.accessService = accessService;
        this.porterService = porterService;
        this.peopleService = peopleService;
    }

    // Methods 
    // To get all the access 
    @GetMapping
    public List<Access> getAllAccess() {
        return accessService.getAllAccesses();
    }

    // To get an access for the id 
    @GetMapping("/{id}")
    public Access getAccessById(@PathVariable String id) {
        return accessService.getAccessById(id);
    }

    // To create a new access 
    @PostMapping("/people/{idpeople}")
    public ResponseEntity<Access> createAccessWithPersonAndPorter(@PathVariable String idpeople,
            @RequestBody Access acccess) {
        People people = peopleService.getPeopleById(idpeople);
        if (people != null) {

            acccess.setPeople(people);
            Access savedAccess = accessService.saveAccess(acccess);

            return new ResponseEntity<>(savedAccess, HttpStatus.CREATED);
        }
        return null;
    }

    // To delete an access 
    @DeleteMapping("/{id}")
    public void deleteAccess(@PathVariable String id) {
        accessService.deleteAccess(id);
    }

    //To add a note to an access 
    @PostMapping("/add-note/{idAccess}")
    public Access addAccessNoteToAccess(@PathVariable String idAccess, @RequestBody AccessNote accessNote) {
        return accessService.addAccessNoteToAccess(idAccess, accessNote);
    }

    //To remove a note to an access 
    @DeleteMapping("/delete-note/{idAccessNote}")
    public Access deleteAccessNoteToAccess(@PathVariable String idAccessNote) {
        return accessService.removeAccessNoteFromAccess(idAccessNote);
    }

    // To add a porter to an access
    @PostMapping("/add-porter/{id}/{idPorter}")
    public Access addPorterToAccess(@PathVariable String id, @PathVariable String idPorter) {
        return accessService.addPorterToAccess(id, idPorter);
    }

    // To delete a porter from an access 
    @DeleteMapping("delete-porter/{id}/{idPorter}")
    public Access removePorterFromAccess(@PathVariable String id, @PathVariable String idPorter) {
        return accessService.removePorterFromAccess(id, idPorter);
    }

    // To get al the access between two dates 
    @GetMapping("/dates")
    public List<Access> findAccessBetweenDates(@RequestParam Date startDate, @RequestParam Date endDate) {
        return accessService.findAccessBetweenDates(startDate, endDate);
    }

    // To update a porter in an access 
    @PutMapping("/{idAccess}/porters/{idPorter}")
    public Access updatePorterInAccess(@PathVariable String idAccess, @PathVariable String idPorter) {
        Access access = accessService.getAccessById(idAccess);
        Porters porter = porterService.getPorterById(idPorter);
        access.getPorters().add(porter);
        return accessService.saveAccess(access);
    }

    @PutMapping("/{idAccess}")
    public Access updateAccess(@PathVariable String idAccess, @RequestBody Access updatedAccess) {
        Access access = accessService.getAccessById(idAccess);

        if (access != null) {

            if (updatedAccess.getEntryAccess() != null) {
                access.setEntryAccess(updatedAccess.getEntryAccess());
            }
            if (updatedAccess.getExitAccess() != null) {
                access.setExitAccess(updatedAccess.getExitAccess());
            }
            return accessService.saveAccess(access);
        }

        return null;

    }

}
