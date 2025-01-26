package campus.u2.entrysystem.people.infrastructure;

import campus.u2.entrysystem.people.application.PeopleService;
import campus.u2.entrysystem.people.domain.People;
import campus.u2.entrysystem.company.domain.Company;
import campus.u2.entrysystem.registeredequipment.domain.RegisteredEquipment;
import campus.u2.entrysystem.Utilities.exceptions.GlobalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @PostMapping
    public ResponseEntity<People> savePeople(@RequestBody People people) {

        People savedPeople = peopleService.savePeople(people);
        return ResponseEntity.ok(savedPeople);
    }

    @PostMapping("/{personId}/addEquipment")
    public ResponseEntity<People> addEquipmentToPerson(@PathVariable Long personId,
            @RequestBody RegisteredEquipment equipment) {

        People updatedPeople = peopleService.addEquipmentToPerson(personId, equipment);
        return ResponseEntity.ok(updatedPeople);

    }

    @DeleteMapping("/{personId}/removeEquipment/{equipmentId}")
    public ResponseEntity<People> removeEquipmentFromPerson(@PathVariable Long personId,
            @PathVariable Long equipmentId) {

        People updatedPeople = peopleService.removeEquipmentFromPerson(personId, equipmentId);
        return ResponseEntity.ok(updatedPeople);
    }

    @GetMapping("/{peopleId}/equipment")
    public ResponseEntity<List<RegisteredEquipment>> findEquipmentByPeopleId(@PathVariable Long peopleId) {
        List<RegisteredEquipment> equipmentList = peopleService.findEquipmentByPeopleId(peopleId);
        return ResponseEntity.ok(equipmentList);

    }

    @GetMapping
    public ResponseEntity<List<People>> listAllPeople() {
        List<People> peopleList = peopleService.listAllPeople();
        return ResponseEntity.ok(peopleList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<People> getPeopleById(@PathVariable Long id) {

        People people = peopleService.getPeopleById(id);
        return ResponseEntity.ok(people);

    }

    @GetMapping("/cedula/{cedula}")
    public ResponseEntity<People> getPeopleByCedula(@PathVariable String cedula) {
        People people = peopleService.getPeopleByCedula(cedula);
        return ResponseEntity.ok(people);

    }

    @DeleteMapping("/{cedula}")
    public ResponseEntity<Void> deletePeople(@PathVariable String cedula) {
        peopleService.deletePeople(cedula);
        return ResponseEntity.ok().build();
    }
    
    
    // CUERPO PARA UPDATE 
    
    //	{
//		"id": 24,
//		"name": "BBBB",
//		"cedula": "1111111111",
//		"telefono": "2222222",
//		"personType": true
//	} 

    @PutMapping
    public ResponseEntity<People> updatePeople(@RequestBody People people) {
        People peopleToUpdate = peopleService.getPeopleById(people.getId());
        if (peopleToUpdate == null) {
            return ResponseEntity.notFound().build();
        }
        peopleToUpdate.setName(people.getName());
        peopleToUpdate.setCedula(people.getCedula());
        peopleToUpdate.setPersonType(people.getPersonType());
        peopleToUpdate.setTelefono(people.getTelefono());
        return ResponseEntity.ok(peopleToUpdate);
    }

}
